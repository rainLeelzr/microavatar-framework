package microavatar.framework.core.mvc;

import microavatar.framework.core.support.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author rain
 */
public abstract class BaseService<
        C extends BaseCriteria,
        D extends BaseDao<C, E>,
        E extends BaseEntity> {

    protected abstract D getDao();

    /**
     * 增
     */
    public int add(E entity) {
        entity.computeIdIfAbsent();
        return getDao().add(entity);
    }

    public int batchAdd(Collection<E> entitys) {
        if (entitys == null || entitys.isEmpty()) {
            return 0;
        }

        // 没有id的实体，设置一个id
        for (E entity : entitys) {
            entity.computeIdIfAbsent();
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
            insertCount += getDao().batchAdd(es);
        }

        return insertCount;
    }

    /**
     * 删
     */
    public int hardDeleteById(Long id) {
        return getDao().hardDeleteById(id);
    }

    public int hardDeleteByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        if (ids.size() == 1) {
            return hardDeleteById(ids.iterator().next());
        }

        // 一次性生成sql的in（）参数个数的限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 2000;

        if (ids.size() <= limit) {
            return getDao().hardDeleteByIds(ids);
        }

        int count = 0;
        Collection<Collection<Long>> split = CollectionUtil
                .split(ids instanceof List ? (List<Long>) ids : new ArrayList<>(ids), limit);
        for (Collection<Long> idArr : split) {
            count += getDao().hardDeleteByIds(idArr);
        }
        return count;
    }

    /**
     * 根据id软删除记录
     *
     * @param id 需要软删除的实体的id
     * @return 软删除成功的记录数量
     */
    public int softDeleteById(Long id) {
        return getDao().softDeleteById(id);
    }

    /**
     * 根据批量id软删除记录
     *
     * @param ids 需要软删除的实体的id集合
     * @return 软删除成功的记录数量
     */
    public int softDeleteByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        if (ids.size() == 1) {
            return softDeleteById(ids.iterator().next());
        }

        // 一次性生成sql的in（）参数个数的限制，防止因业务逻辑的不合理，而影响数据库性能
        int limit = 2000;

        if (ids.size() <= limit) {
            return getDao().softDeleteByIds(ids);
        }

        int count = 0;
        Collection<Collection<Long>> split = CollectionUtil
                .split(ids instanceof List ? (List<Long>) ids : new ArrayList<>(ids), limit);
        for (Collection<Long> idArr : split) {
            count += getDao().softDeleteByIds(idArr);
        }
        return count;
    }

    /**
     * 改
     */
    public int updateAllColumnsById(E entity) {
        return getDao().updateAllColumnsById(entity);
    }

    public int updateExcludeNullFieldsById(E entity) {
        return getDao().updateExcludeNullFieldsById(entity);
    }

    public int updateAllColumnsByCriteria(E entity, C criteria) {
        return getDao().updateAllColumnsByCriteria(entity, criteria);
    }

    public int updateExcludeNullFieldsByCriteria(E entity, C criteria) {
        return getDao().updateExcludeNullFieldsByCriteria(entity, criteria);
    }

    /**
     * 查
     */
    public E getById(Long id) {
        return getDao().getById(id);
    }

    public List<E> findByCriteria(C criteria) {
        return getDao().findByCriteria(criteria);
    }

    public long countByCriteria(C criteria) {
        return getDao().countByCriteria(criteria);
    }

    public List<E> findAll() {
        return getDao().findAll();
    }

    public long countAll() {
        return getDao().countAll();
    }

}
