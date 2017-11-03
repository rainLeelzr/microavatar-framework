package microavatar.framework.core.cache.ehcache;

import microavatar.framework.common.util.log.LogUtil;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class EhcacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        LogUtil.getLogger().debug(
                "ehcache缓存事件: {}， Key: {} 旧值: {} 新值: {}",
                event.getType(),
                event.getKey(),
                event.getOldValue(),
                event.getNewValue()
        );
    }
}
