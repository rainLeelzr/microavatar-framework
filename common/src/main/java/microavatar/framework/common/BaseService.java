package microavatar.framework.common;

import microavatar.framework.util.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseService<E extends BaseEntity, D extends BaseDao<E>> {

    protected abstract D getDao();

    /* 增 */
    public int save(E entity) {
        if (entity.getId() == null || entity.getId().length() == 0) {
            entity.generateId();
        }
        return getDao().save(entity);
    }

    public int batchSave(Collection<E> entitys) {
        for (E entity : entitys) {
            if (entity.getId() == null || entity.getId().length() == 0) {
                entity.generateId();
            }
        }
        return getDao().batchSave(entitys);
    }

    /* 删 */
    public int deleteById(String id) {
        return getDao().deleteById(id);
    }

    public int deleteByIds(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        // 一次性生成sql的in（）参数个数的限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 2000;

        if (ids.size() <= limit) {
            return getDao().deleteByIds(ids);
        }

        Integer count = 0;
        Collection<Collection<String>> split = CollectionUtil
                .split(ids instanceof List ? (List<String>) ids : new ArrayList<>(ids), limit);
        for (Collection<String> idArr : split) {
            count += getDao().deleteByIds(idArr);
        }
        return count;
    }

    /* 改 */
    public int update(E entity) {
        return getDao().update(entity);
    }

    /* 查 */
    public E getById(String id) {
        return getDao().getById(id);
    }

    public List<E> findPage(Map<String, ?> params) {
        return getDao().findPage(params);
    }

    public long count(Map<String, ?> params) {
        return (int) PageHelper.count(() -> getDao().findPage(params));
    }

    public long countAll() {
        return getDao().countAll();
    }

}
