package microavatar.framework.core.mvc;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author rain
 */
@Mapper
public interface BaseDao<
        C extends BaseCriteria,
        E extends BaseEntity> {

    //********** 增 start **********

    /**
     * 单条insert
     */
    int add(E entity);

    /**
     * 批量insert
     */
    int batchAdd(Collection<E> entitys);

    //********** 增 end **********

    //********** 删 start **********

    /**
     * 根据id硬删除记录
     *
     * @param id 需要硬删除的实体的id
     * @return 硬删除成功的记录数量
     */
    int hardDeleteById(Long id);

    /**
     * 根据批量id硬删除记录
     *
     * @param ids 需要硬删除的实体的id集合
     * @return 硬删除成功的记录数量
     */
    int hardDeleteByIds(Collection<Long> ids);

    /**
     * 根据id软删除记录
     *
     * @param id 需要软删除的实体的id
     * @return 软删除成功的记录数量
     */
    int softDeleteById(Long id);

    /**
     * 根据批量id软删除记录
     *
     * @param ids 需要软删除的实体的id集合
     * @return 软删除成功的记录数量
     */
    int softDeleteByIds(Collection<Long> ids);

    //********** 删 end **********

    //********** 改 start **********

    /**
     * 根据实体id更新所有字段值到数据库
     * 字段值为null，也会更新到数据库
     */
    int updateAllColumnsById(E entity);

    /**
     * 改
     */
    int updateExcludeNullFieldsById(E entity);

    /**
     * 根据实体id更新所有字段值到数据库
     * 字段值为null，也会更新到数据库
     */
    int updateAllColumnsByCriteria(@Param("entity") E entity, @Param("criteria") C criteria);

    /**
     * 改
     */
    int updateExcludeNullFieldsByCriteria(@Param("entity") E entity, @Param("criteria") C criteria);

    //********** 改 end **********

    //********** 查 start **********

    /**
     * 根据id查询一个实体
     *
     * @param id 需要查询的实体的id
     * @return 完整字段数据的实体
     */
    E getById(Long id);

    /**
     * 根据查询条件查找分页列表
     *
     * @param criteria 查询条件
     * @return 结果列表
     */
    List<E> findByCriteria(@Param("criteria") C criteria);

    long countByCriteria(@Param("criteria") C criteria);

    List<E> findAll();

    long countAll();

    //********** 查 end **********

}
