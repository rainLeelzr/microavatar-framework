package microavatar.framework.core.net.tcp;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author Rain
 */
@Configuration
@Conditional(TcpServerCondition.class)
public class TcpServerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private TcpServer tcpServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        tcpServer.start();
    }
}
