package microavatar.framework;

import microavatar.framework.core.api.ApiManagerApplicationListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/*
  SpringCLoud中的“Discovery Service”有多种实现，比如：eureka, consul, zookeeper。
  1，@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现；
  2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 */
@SpringBootApplication
@EnableEurekaClient
public class ImApplication {

    //启动服务时，开启debug日志模式：java -jar xxx.jar --debug
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(ImApplication.class)
                .web(true)
                .run(args);
    }

    @Bean
    public ApiManagerApplicationListener apiManagerApplicationListener() {
        return new ApiManagerApplicationListener();
    }

}
