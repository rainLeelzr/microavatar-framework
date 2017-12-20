package microavatar.framework.core.cache.ehcache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class EhcacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.debug(
                "ehcache缓存事件: {}， Key: {} 旧值: {} 新值: {}",
                event.getType(),
                event.getKey(),
                event.getOldValue(),
                event.getNewValue()
        );
    }
}
