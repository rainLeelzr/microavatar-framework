package microavatar.framework.core.mq.rocketmq.property;

import java.util.Map;

public class Consumer {

    private String instanceName;

    private Map<String, Subscription> subscriptions;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Map<String, Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Map<String, Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
