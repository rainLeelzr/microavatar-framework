package microavatar.framework.core.cache.redis;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.cache.CacheConfig;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.core.RedisOperations;

import javax.cache.event.EventType;

/**
 * l2级缓存管理器
 */
@Slf4j
public class L2RedisCacheManager extends RedisCacheManager {

    /**
     * 系统启动时的缓存配置
     */
    private CacheConfig cacheConfig;

    /**
     * l1级缓存管理器
     */
    private CacheManager l1CacheManager;

    /**
     * 构造方法
     *
     * @param redisOperations redis操作类
     * @param l1CacheManager  l1级缓存管理器
     * @param cacheConfig     缓存配置
     */
    public L2RedisCacheManager(CacheConfig cacheConfig,
                               CacheManager l1CacheManager,
                               RedisOperations redisOperations,
                               RedisCachePrefix redisCachePrefix) {
        super(redisOperations);
        this.cacheConfig = cacheConfig;
        this.l1CacheManager = l1CacheManager;
        setUsePrefix(true);
        setCachePrefix(redisCachePrefix);
    }

    /**
     * 创建一个缓存
     *
     * @param cacheName 缓存名称
     * @return RedisCache
     */
    @SuppressWarnings("unchecked")
    @Override
    protected RedisCache createCache(String cacheName) {
        long expiration = computeExpiration(cacheName);
        return new L2RedisCache(
                l1CacheManager,
                this,
                cacheName,
                (this.isUsePrefix() ? this.getCachePrefix().prefix(cacheName) : null),
                this.getRedisOperations(),
                expiration);
    }

    /**
     * 当缓存发生变化时，调用此方法
     *
     * @param cacheName 缓存名称
     * @param key       key
     */
    public void onCacheChanged(EventType eventType, String cacheName, Object key) {
            /*
              redis缓存发生变化时，则发布一条消息：
              topic格式: ${spring.application.name}-cache
              内容格式: cacheName:EventType:key
              EventType为javax.cache.event.EventType的枚举值
            */
        String topicName;
        if (EventType.CREATED == eventType) {
            topicName = cacheConfig.getCreateEventTopic();
        } else if (EventType.REMOVED == eventType) {
            topicName = cacheConfig.getRemoveEventTopic();
        } else {
            log.warn("l2缓存发生变化，未捕捉名称为{}的事件，将不会通知其他进程进行缓存更新！", eventType);
            return;
        }

        String message = new StringBuilder()
                .append(cacheName)
                .append(((AvatarRedisCachePrefix) this.getCachePrefix()).getDelimiter())
                .append(key.toString())
                .toString();
        this.getRedisOperations().convertAndSend(topicName, message);
        log.debug("l2缓存发生变化，已发出名称为{}的事件，通知其他进程进行缓存更新！", eventType);
    }

    public AvatarRedisCachePrefix getL2RedisCachePrefix() {
        return (AvatarRedisCachePrefix) this.getCachePrefix();
    }

}