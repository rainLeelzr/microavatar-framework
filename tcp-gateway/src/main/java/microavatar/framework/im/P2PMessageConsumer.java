package microavatar.framework.im;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.mq.rocketmq.RocketmqEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class P2PMessageConsumer implements ApplicationListener<RocketmqEvent> {

    @Override
    public void onApplicationEvent(RocketmqEvent event) {
        if (!"p2pMessage".equals(event.getTopic())) {
            return;
        }

        String msg = event.getStringMsg();

        log.debug("解析到mq消息内容：{}", msg);

    }
}
