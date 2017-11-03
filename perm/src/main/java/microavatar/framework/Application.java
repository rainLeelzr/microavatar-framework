package microavatar.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
@EnableCaching
@EnableScheduling
@EnableEurekaClient
public class Application {

    //启动服务时，开启debug日志模式：java -jar xxx.jar --debug
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
    }

}
