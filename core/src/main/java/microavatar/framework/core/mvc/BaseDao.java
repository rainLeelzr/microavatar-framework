package microavatar.framework.core.mvc;

import microavatar.framework.core.database.SqlCondition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface BaseDao<E extends BaseEntity> {

    /**
     * 每条sql的查询结果集数量限制
     */
    int FIND_PAGE_COUNT_LIMIT = 5000;

    /** 增 */
    int save(E entity);

    int batchSave(Collection<E> entitys);

    /** 删 */
    int deleteById(String id);

    int deleteByIds(Collection<String> ids);

    /** 改 */
    int update(E entity);

    /** 查 */
    E getById(String id);

    List<E> findPage(Map<String, ?> params);

    default List<E> findAll() {
        long count = countAll();
        if (count > FIND_PAGE_COUNT_LIMIT) {
            throw new IllegalArgumentException(String.format(
                    "countAll数量为%s，超过%s，为避免影响数据库性能，禁止此条超大结果集查询，请在service层分段处理！",
                    count,
                    FIND_PAGE_COUNT_LIMIT));
        }

        return findPage(new SqlCondition().select(E.ALL_COLUMNS).build());
    }

    long countAll();

}
