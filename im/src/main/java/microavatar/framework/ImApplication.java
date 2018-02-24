package microavatar.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringCLoud中的“Discovery Service”有多种实现，比如：eureka, consul, zookeeper。
 * 1，@EnableDiscoveryClient 注解是基于spring-cloud-commons依赖，并且在classpath中实现；
 * 2，@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ImApplication {

    /**
     * 启动服务时，开启debug日志模式：java -jar xxx.jar --debug
     */
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(ImApplication.class)
                .web(true)
                .run(args);
    }

}
