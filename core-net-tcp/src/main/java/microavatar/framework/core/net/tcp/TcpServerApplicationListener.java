package microavatar.framework.core.net.tcp;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

@Configuration
@Conditional(TcpServerCondition.class)
public class TcpServerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private TcpServer tcpServer;

    private boolean alreadyInitialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadyInitialized) {
            return;
        }

        new Thread("netty-starter") {

            @Override
            public void run() {
                tcpServer.start();
            }
        }.start();

        alreadyInitialized = true;
    }

}
