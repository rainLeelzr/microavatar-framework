package microavatar.framework.core.cache.redis;

import microavatar.framework.core.util.log.LogUtil;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.cache.event.EventType;
import java.io.UnsupportedEncodingException;

public class RedisEventMessageListener implements MessageListener {

    private static final Logger logger = LogUtil.getLogger();

    private CacheManager l1CacheManager;

    private L2RedisCacheManager l2CacheManager;

    public RedisEventMessageListener(CacheManager l1CacheManager, L2RedisCacheManager cacheManager) {
        this.l1CacheManager = l1CacheManager;
        this.l2CacheManager = cacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topicName;
        String event;
        try {
            topicName = new String(message.getChannel(), "UTF-8");
            event = topicName.split(l2CacheManager.getL2RedisCachePrefix().getDelimiter())[1];
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            return;
        }

        String messageBody;
        try {
            messageBody = new String(message.getBody(), "UTF-8");
            if (messageBody.startsWith("\"") && messageBody.endsWith("\"")) {
                messageBody = messageBody.substring(1, messageBody.length() - 1);
            } else if (messageBody.startsWith("\"")) {
                messageBody = messageBody.substring(1);
            } else if (messageBody.endsWith("\"")) {
                messageBody = messageBody.substring(0, messageBody.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            return;
        }

        logger.debug("接收到redis消息通知，topicName: {}，messageBody: {}，event：{}", topicName, messageBody, event);

        if (messageBody.length() == 0) {
            logger.warn("messageBody.length()=0, 取消缓存更新任务！");
            return;
        }

        String[] split = messageBody.split(l2CacheManager.getL2RedisCachePrefix().getDelimiter());
        String cacheName = split[0];

        StringBuilder key = new StringBuilder();
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                key.append(split[i]);
                if (i + 1 != split.length) {
                    key.append(l2CacheManager.getL2RedisCachePrefix().getDelimiter());
                }
            }
        }

        if (EventType.REMOVED.toString().equals(event)) {
            Cache l1Cache = l1CacheManager.getCache(cacheName);
            if (l1Cache == null) {
                logger.warn("找不到cacheName为{}的l1Cache, 取消l1Cache清理任务", cacheName);
                return;
            }

            logger.debug("正在清除l1Cache缓存{}中key={}的缓存", cacheName, key);
            l1Cache.evict(key);
        }

    }
}
