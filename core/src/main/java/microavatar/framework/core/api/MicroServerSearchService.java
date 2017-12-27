package microavatar.framework.core.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.api.model.ServerApi;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微服务查找服务
 * 查找更更新注册在eureka上的微服务的api
 */
@Slf4j
public class MicroServerSearchService implements InitializingBean {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate noBalanceRestTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    /**
     * 各个微服务提供可以进行tcp访问的api集合
     * key:   微服务的名称
     * value: {key: urlMapping，value: Api}
     * 在初始化时，必须重新new map，因为微服务有可以删除了一些api，如果此处的map，在更新时，只用put来覆盖原值，则不能清空已被删除的api
     */
    private Map<String, ServerApi> microServerApis = Collections.emptyMap();

    public ServerApi getMicroServerByServerName(String serverName) {
        return microServerApis.get(serverName);
    }

    public Map<String, ServerApi> getMicroServerApis() {
        return microServerApis;
    }

    /**
     * 更新在Eureka上注册了的可以提供tcp的服务
     */
    @Scheduled(initialDelay = 1000 * 30, fixedRate = 1000 * 30)
    @SuppressWarnings("unchecked")
    public void updateEurekaTcpServer() {
        List<String> services = discoveryClient.getServices();
        // 更新成功的个数
        int updateCount = 0;
        Map<String, ServerApi> tempMicroServerApis = new HashMap<>();
        for (String serverName : services) {
            if (serverName.equalsIgnoreCase(applicationName)) {
                continue;
            }

            List<ServiceInstance> instances = discoveryClient.getInstances(serverName);

            if (instances.isEmpty()) {
                continue;
            }

            // 在此serverName的服务集群下，最后启动的那个服务的api初始化时间
            long lastInitTime = 0;

            // 在此serverName的服务集群下，最后启动的那个服务的下标
            int index = -1;

            for (int i = 0; i < instances.size(); i++) {
                try {
                    String initTimeUrl = instances.get(i).getUri() + "/api/initTime";
                    long initTime = noBalanceRestTemplate.getForObject(initTimeUrl, long.class);

                    ServerApi serverApi = microServerApis.get(serverName.toUpperCase());
                    if (serverApi == null) {
                        if (initTime > lastInitTime) {
                            lastInitTime = initTime;
                            index = i;
                        }
                    } else if (initTime > serverApi.getTime()) {
                        lastInitTime = initTime;
                        index = i;
                    }
                } catch (Exception e) {
                    log.debug("获取微服务[{}]提供的Api initTime失败：{}", serverName, e.getMessage());
                }
            }

            if (index != -1) {
                log.debug("正在更新微服务[{}]的api, url: {}", serverName, instances.get(index).getUri());
                String getApisUrl = instances.get(index).getUri() + "/api";
                try {
                    String json = noBalanceRestTemplate.getForObject(getApisUrl, String.class);
                    ServerApi serverApi = JSON.parseObject(json, ServerApi.class);
                    tempMicroServerApis.put(serverName.toUpperCase(), serverApi);
                    updateCount++;
                    log.debug("更新微服务[{}]api成功：{}", serverName, serverApi);
                } catch (Exception e) {
                    log.info("获取微服务[{}]提供的Tcp api失败：{}", serverName, e.getMessage());
                }
            }
        }
        log.debug("已更新[{}]个微服务的api", updateCount);
        if (updateCount > 0) {
            this.microServerApis = tempMicroServerApis;
            // 发出微服务已更新的时间
            eventPublisher.publishEvent(new MicroServerUpdatedEvent(microServerApis));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        updateEurekaTcpServer();
    }

}
