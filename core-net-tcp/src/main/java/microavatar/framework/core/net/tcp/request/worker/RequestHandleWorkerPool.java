package microavatar.framework.core.net.tcp.request.worker;

import microavatar.framework.core.api.MicroServerService;
import microavatar.framework.core.net.tcp.TcpServerCondition;
import microavatar.framework.core.net.tcp.request.ATCPRequest;
import microavatar.framework.core.serialization.Serializer;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理业务逻辑工人线程线程池
 * 分配式
 */
@Configuration
@Conditional(TcpServerCondition.class)
public class RequestHandleWorkerPool implements InitializingBean {

    private int minWorkerCount = 5;//最少的工人队列

    private int maxWorkerCount = 30;//最大的工人队列

    private RequestHandleWorker[] workers;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MicroServerService microServerService;

    @Resource
    private Serializer serializer;

    @Resource
    private ZuulProperties zuulProperties;

    /**
     * 初始化工作线程
     */
    public void initWorkers() {
        workers = new RequestHandleWorker[this.minWorkerCount];
        Map<String, String> serverNameMapping = new HashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : zuulProperties.getRoutes().entrySet()) {
            serverNameMapping.put(entry.getKey(), entry.getValue().getServiceId());
        }
        for (int i = 0; i < workers.length; i++) {
            RequestHandleWorker worker = new RequestHandleWorker(
                    restTemplate,
                    microServerService,
                    serializer,
                    serverNameMapping,
                    "worker-" + i);
            workers[i] = worker;
            worker.start();
        }
    }

    /**
     * 将网络事件包分配到指定的
     */
    public void putRequestInQueue(ATCPRequest request) {
        int index = RandomUtils.nextInt(0, workers.length);
        RequestHandleWorker worker = workers[index];
        worker.acceptRequest(request);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initWorkers();
    }

    public static void main(String[] args) {
        int i = 55 % 5;
        System.out.println(i);
    }
}
