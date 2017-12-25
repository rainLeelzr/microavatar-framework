package microavatar.framework;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/*
  SpringCLoud中的“Discovery Service”有多种实现，比如：eureka, consul, zookeeper。
  1，@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现；
  2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableZuulProxy
@EnableScheduling
@EnableFeignClients
@ComponentScan
public class GatewayTcpApplication {

    //启动服务时，开启debug日志模式：java -jar xxx.jar --debug
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(GatewayTcpApplication.class)
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

}
