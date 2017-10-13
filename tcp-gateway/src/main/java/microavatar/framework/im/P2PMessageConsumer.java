package microavatar.framework.im;

import microavatar.framework.core.mq.rocketmq.RocketmqEvent;
import microavatar.framework.core.util.log.LogUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class P2PMessageConsumer implements ApplicationListener<RocketmqEvent> {

    @Override
    public void onApplicationEvent(RocketmqEvent event) {
        if (!"p2pMessage".equals(event.getTopic())) {
            return;
        }

        String msg = event.getStringMsg();

        LogUtil.getLogger().debug("解析到mq消息内容：{}", msg);

    }
}
