package microavatar.framework.core.cache.redis;

import microavatar.framework.core.util.log.LogUtil;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisOperations;

import javax.cache.event.EventType;

/**
 * l2级缓存
 */
public class L2RedisCache extends RedisCache {

    private static Logger logger = LogUtil.getLogger();

    /**
     * l1级缓存管理器
     */
    private CacheManager l1CacheManager;

    /**
     * l2级缓存管理器
     */
    private L2RedisCacheManager l2RedisCacheManager;

    /**
     * 构造方法
     *
     * @param l2RedisCacheManager l2级缓存管理器
     * @param l1CacheManager      l1级缓存管理器
     * @param cacheName           缓存名称
     * @param prefix              前缀
     * @param redisOperations     redis操作类
     * @param expiration          过期时间
     */
    L2RedisCache(CacheManager l1CacheManager,
                 L2RedisCacheManager l2RedisCacheManager,
                 String cacheName,
                 byte[] prefix,
                 RedisOperations<? extends Object, ? extends Object> redisOperations,
                 long expiration) {
        super(cacheName, prefix, redisOperations, expiration);
        this.l2RedisCacheManager = l2RedisCacheManager;
        this.l1CacheManager = l1CacheManager;
    }

    @Override
    public ValueWrapper get(Object key) {
        String cacheName = super.getName();
        Cache l1Cache = l1CacheManager.getCache(cacheName);
        logger.debug("get,l1CacheManager:{}", l1CacheManager);
        logger.debug("get,cacheName:{}", cacheName);
        logger.debug("get,l1Cache:{}", l1Cache);
        ValueWrapper valueWrapper = l1Cache.get(key);
        Object value = null;

        if (logger.isDebugEnabled()) {
            if (valueWrapper != null && valueWrapper.get() != null) {
                value = valueWrapper.get();
            }
            logger.debug("获取L1缓存: key: {}, value: {}", key, value);
        }

        if (valueWrapper == null) {
            valueWrapper = super.get(key);
            if (valueWrapper != null && valueWrapper.get() != null) {
                value = valueWrapper.get();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("获取L2缓存: key: {}, value: {}", key, value);
            }

            if (value != null) {
                l1Cache.put(key, valueWrapper.get());
            }
        }
        return valueWrapper;
    }

    @Override
    public void put(final Object key, final Object value) {
        String cacheName = super.getName();
        Cache l1Cache = l1CacheManager.getCache(cacheName);
        l1Cache.put(key, value);
        super.put(key, value);
        l2RedisCacheManager.onCacheChanged(EventType.CREATED, super.getName(), key);
    }

    @Override
    public void evict(Object key) {
        String cacheName = super.getName();
        Cache l1Cache = l1CacheManager.getCache(cacheName);
        l1Cache.evict(key);
        super.evict(key);
        l2RedisCacheManager.onCacheChanged(EventType.REMOVED, super.getName(), key);
    }


    @Override
    public ValueWrapper putIfAbsent(Object key, final Object value) {
        String cacheName = super.getName();
        Cache l1Cache = l1CacheManager.getCache(cacheName);
        l1Cache.putIfAbsent(key, value);
        ValueWrapper wrapper = super.putIfAbsent(key, value);
        l2RedisCacheManager.onCacheChanged(EventType.CREATED, super.getName(), key);
        return wrapper;
    }

    @Override
    public void clear() {
        super.clear();
        l2RedisCacheManager.onCacheChanged(EventType.REMOVED, super.getName(), "");
    }
}
