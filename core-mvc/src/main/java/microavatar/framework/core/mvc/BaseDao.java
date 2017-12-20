package microavatar.framework.core.mvc;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author rain
 */
@Mapper
public interface BaseDao<E extends BaseEntity> {

    /**
     * 每条sql的查询结果集数量限制
     */
    int FIND_PAGE_COUNT_LIMIT = 10000;

    /**
     * 增
     */
    int add(E entity);

    int batchAdd(Collection<E> entitys);

    /**
     * 删
     */
    int deleteById(Long id);

    int deleteByIds(Collection<Long> ids);

    /**
     * 改
     */
    int update(E entity);

    /**
     * 查
     */
    E getById(Long id);

    List<E> findPage(Map<String, ?> params);

    @SuppressWarnings("unchecked")
    default List<E> findAll() {
        long count = countAll();
        if (count > FIND_PAGE_COUNT_LIMIT) {
            throw new IllegalArgumentException(String.format(
                    "countAll数量为%s，超过%s，为避免影响数据库性能，禁止此条超大结果集查询，请在service层分段处理！",
                    count,
                    FIND_PAGE_COUNT_LIMIT));
        }

        return findPage(MapUtils.EMPTY_MAP);
    }

    long countAll();

}
