package microavatar.framework.core.cache.memory;

import microavatar.framework.core.mvc.BaseEntity;
import microavatar.framework.common.util.log.LogUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.List;

/**
 * 实体缓存接口，此缓存应用于相对固定内容的实体，如系统参数、错误信息
 *
 * @param <T> 需要缓存的具体实体
 */
public abstract class EntityCache<T extends BaseEntity> implements InitializingBean {

    /**
     * 参数表，缓存在内存中，定期刷新最新数据
     */
    private List<T> paramsCache = Collections.emptyList();

    /**
     * 第一次加载实体，用于系统启动时调用，当抛异常时，终止启动系统
     */
    public void firstLoad() {
        try {
            List<T> newEntitys = doLoad();
            if (checkLoaded(newEntitys)) {
                paramsCache = newEntitys;
            }
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 重新加载实体，但加载实体报错，或则加载实体后校验实体失败，则取消重新加载，继续使用之前加载到的实体
     */
    @Scheduled(initialDelay = 1000 * 60 * 30, fixedRate = 1000 * 60 * 30)
    public void reload() {
        try {
            List<T> newEntitys = doLoad();
            if (checkLoaded(newEntitys)) {
                paramsCache = newEntitys;
            }
        } catch (Exception e) {
            LogUtil.getLogger().error("忽略本次参数reload，请尽快检查修正！{}", e.getMessage(), e);
        }
    }

    /**
     * 加载实体的具体逻辑
     */
    public abstract List<T> doLoad();

    /**
     * 检查加载到的实体是否符合标准
     */
    public abstract boolean checkLoaded(List<T> newEntitys);

    @Override
    public void afterPropertiesSet() throws Exception {
        firstLoad();
    }

}
