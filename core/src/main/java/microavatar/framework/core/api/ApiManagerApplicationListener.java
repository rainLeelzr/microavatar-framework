package microavatar.framework.core.api;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

public class ApiManagerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private LocalApiManager apiManager;

    private boolean alreadyInitialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadyInitialized) {
            return;
        }

        if (apiManager.getInitTime() == 0) {
            apiManager.init();
        }

        alreadyInitialized = true;
    }

}
