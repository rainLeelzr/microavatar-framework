package microavatar.framework.core.mvc;

import com.github.pagehelper.PageHelper;
import microavatar.framework.core.support.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author rain
 */
public abstract class BaseService<E extends BaseEntity, D extends BaseDao<E>> {

    protected abstract D getDao();

    /**
     * 增
     */
    public int add(E entity) {
        if (entity.getId() == null) {
            entity.newId();
        }
        return getDao().add(entity);
    }

    public int batchAdd(Collection<E> entitys) {
        if (entitys == null || entitys.isEmpty()) {
            return 0;
        }

        // 一次性批量插入限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 5000;
        if (entitys.size() <= limit) {
            return getDao().batchAdd(entitys);
        }

        int insertCount = 0;
        Collection<Collection<E>> split = CollectionUtil
                .split(entitys instanceof List ? (List<E>) entitys : new ArrayList<>(entitys), limit);

        for (Collection<E> es : split) {
            for (E entity : es) {
                if (entity.getId() == null) {
                    entity.newId();
                }
            }
            insertCount += getDao().batchAdd(es);
        }

        return insertCount;
    }

    /**
     * 删
     */
    public int deleteById(Long id) {
        return getDao().deleteById(id);
    }

    public int deleteByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        // 一次性生成sql的in（）参数个数的限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 2000;

        if (ids.size() <= limit) {
            return getDao().deleteByIds(ids);
        }

        int count = 0;
        Collection<Collection<Long>> split = CollectionUtil
                .split(ids instanceof List ? (List<Long>) ids : new ArrayList<>(ids), limit);
        for (Collection<Long> idArr : split) {
            count += getDao().deleteByIds(idArr);
        }
        return count;
    }

    /**
     * 改
     */
    public int update(E entity) {
        return getDao().update(entity);
    }

    /**
     * 查
     */
    public E getById(Long id) {
        return getDao().getById(id);
    }

    public List<E> findPage(Map<String, ?> params) {
        return getDao().findPage(params);
    }

    public List<E> findAll() {
        return getDao().findAll();
    }

    public long count(Map<String, ?> params) {
        return (int) PageHelper.count(() -> getDao().findPage(params));
    }

    public long countAll() {
        return getDao().countAll();
    }

}
