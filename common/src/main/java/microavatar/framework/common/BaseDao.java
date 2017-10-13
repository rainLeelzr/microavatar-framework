package microavatar.framework.common;

import microavatar.framework.util.sql.SqlCondition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<E extends BaseEntity> {

    /**
     * 每条sql的查询结果集数量限制
     */
    int findPageCountLimit = 5000;

    /* 增 */
    int save(E entity);

    int batchSave(Collection<E> entitys);

    /* 删 */
    int deleteById(String id);

    int deleteByIds(Collection<String> ids);

    /* 改 */
    int update(E entity);

    /* 查 */
    E getById(String id);

    List<E> findPage(Map<String, ?> params);

    default List<E> findAll() {
        long count = countAll();
        if (count > findPageCountLimit) {
            throw new IllegalArgumentException(String.format(
                    "countAll数量为%s，超过%s，为避免影响数据库性能，禁止此条超大结果集查询，请在service层分段处理！",
                    count,
                    findPageCountLimit));
        }

        return findPage(new SqlCondition().select(E.ALL_COLUMNS).build());
    }

    long countAll();

}
