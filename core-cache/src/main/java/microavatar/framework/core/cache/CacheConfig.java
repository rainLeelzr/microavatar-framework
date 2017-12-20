package microavatar.framework.core.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.cache.redis.AvatarRedisCachePrefix;
import microavatar.framework.core.cache.redis.L2RedisCacheManager;
import microavatar.framework.core.cache.redis.RedisEventMessageListener;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.cache.event.EventType;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Administrator
 */
@Configuration
@Conditional(StarterCacheCondition.class)
@Slf4j
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${spring.application.name}")
    private String projectName;

    /**
     * 缓存过期时间,秒
     */
    private long defaultExpiration = 60;

    private String createEventTopic = "";

    private String removeEventTopic = "";

    @Bean(name = "l1CacheManager")
    public CacheManager cacheManager() {
        URI uri = null;
        try {
            URL resource = this.getClass().getClassLoader().getResource("ehcache.xml");
            if (resource == null) {
                log.error("找不到ehcache.xml文件！");
                System.exit(0);
            }
            uri = resource.toURI();
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
        EhcacheCachingProvider ehcacheCachingProvider = new EhcacheCachingProvider();
        javax.cache.CacheManager cacheManager = ehcacheCachingProvider.getCacheManager(uri, this.getClass().getClassLoader());
        return new JCacheCacheManager(cacheManager);
    }

    @Bean(name = "l2CacheManager")
    @Primary
    public L2RedisCacheManager cacheManager(CacheManager l1CacheManager,
                                            RedisTemplate<String, String> redisTemplate,
                                            RedisCachePrefix redisCachePrefix) {
        L2RedisCacheManager cacheManager = new L2RedisCacheManager(this, l1CacheManager, redisTemplate, redisCachePrefix);
        //设置缓存过期时间
        cacheManager.setDefaultExpiration(defaultExpiration);
        return cacheManager;
    }

    @Bean
    public RedisCachePrefix redisCachePrefix() {
        return new AvatarRedisCachePrefix(projectName, ":");
    }

    @Bean
    public RedisMessageListenerContainer container(CacheManager l1CacheManager,
                                                   L2RedisCacheManager l2CacheManager,
                                                   RedisConnectionFactory connectionFactory) {
        createEventTopic = projectName + l2CacheManager.getL2RedisCachePrefix().getDelimiter() + EventType.CREATED;
        removeEventTopic = projectName + l2CacheManager.getL2RedisCachePrefix().getDelimiter() + EventType.REMOVED;

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(
                new MessageListenerAdapter(
                        new RedisEventMessageListener(l1CacheManager, l2CacheManager)
                ),
                Arrays.asList(new PatternTopic(createEventTopic), new PatternTopic(removeEventTopic))
        );
        return container;
    }

    /**
     * RedisTemplate配置
     */
    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //定义key序列化方式
        //RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
        //定义value的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // template.setKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    public String getCreateEventTopic() {
        return createEventTopic;
    }

    public String getRemoveEventTopic() {
        return removeEventTopic;
    }
}