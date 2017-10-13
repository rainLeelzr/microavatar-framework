package microavatar.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/*
  SpringCLoud中的“Discovery Service”有多种实现，比如：eureka, consul, zookeeper。
  1，@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现；
  2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class Application {

    //启动服务时，开启debug日志模式：java -jar xxx.jar --debug
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(Application.class)
                .web(true)
                .run(args);
    }

}
