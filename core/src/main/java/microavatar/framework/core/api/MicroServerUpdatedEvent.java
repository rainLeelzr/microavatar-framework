package microavatar.framework.core.api;

import org.springframework.context.ApplicationEvent;

/**
 * 有微服务更新成功后，发出此时间
 */
public class MicroServerUpdatedEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MicroServerUpdatedEvent(Object source) {
        super(source);
    }
}
