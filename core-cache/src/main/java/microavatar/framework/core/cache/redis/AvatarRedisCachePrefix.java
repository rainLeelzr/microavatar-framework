package microavatar.framework.core.cache.redis;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class AvatarRedisCachePrefix implements RedisCachePrefix {

    private final RedisSerializer serializer = new StringRedisSerializer();

    private String delimiter = ":";

    private String projectName = "default";

    public AvatarRedisCachePrefix() {
    }

    public AvatarRedisCachePrefix(String projectName, String delimiter) {
        if (projectName != null && projectName.length() != 0) {
            this.projectName = projectName;
        }

        if (delimiter != null && delimiter.length() != 0) {
            this.delimiter = delimiter;
        }
    }

    @SuppressWarnings("unchecked")
    public byte[] prefix(String cacheName) {
        return serializer.serialize(projectName + delimiter + cacheName + delimiter);
    }

    public String getDelimiter() {
        return delimiter;
    }
}
