package microavatar.framework;

import microavatar.framework.core.api.MicroServerService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/*
  SpringCLoud中的“Discovery Service”有多种实现，比如：eureka, consul, zookeeper。
  1，@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现；
  2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableScheduling
public class Application {

    //启动服务时，开启debug日志模式：java -jar xxx.jar --debug
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .web(true)
                .run(args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate noBalanceRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MicroServerService microServerService(){
        return new MicroServerService();
    }

}
