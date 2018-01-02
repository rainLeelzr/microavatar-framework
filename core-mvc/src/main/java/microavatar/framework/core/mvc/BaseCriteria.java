package microavatar.framework.core.mvc;

import lombok.*;

import java.util.Set;

/**
 * 基于mysql的条件
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseCriteria {

    /**
     * 需要查询的字段集合
     */
    private Set<String> selectColumns;

    // ********** 主键 id **********
    /**
     * 主键的值小于
     */
    protected Long idLessThan;

    /**
     * 主键的值小于等于
     */
    protected Long idLessThanEquals;

    /**
     * 主键的值等于
     */
    protected Long idEquals;

    /**
     * 主键的值大于等于
     */
    protected Long idGreaterThanEquals;

    /**
     * 主键的值大于
     */
    protected Long idGreaterThan;

    /**
     * 主键的值不等于
     */
    protected Long idNotEquals;

    /**
     * 主键的值包含在
     */
    protected Set<Long> idIn;

    /**
     * 主键的值不包含在
     */
    protected Set<Long> idNotIn;

    /**
     * 主键的值介于开始
     * mysql between是包含边界值
     */
    protected Long idBetweenStart;

    /**
     * 主键的值介于结束
     * mysql between是包含边界值
     */
    protected Long idBetweenEnd;

    // ********** 创建时间戳 createTime **********
    /**
     * 创建时间戳的值小于
     */
    protected Long createTimeLessThan;

    /**
     * 创建时间戳的值小于等于
     */
    protected Long createTimeLessThanEquals;

    /**
     * 创建时间戳的值等于
     */
    protected Long createTimeEquals;

    /**
     * 创建时间戳的值大于等于
     */
    protected Long createTimeGreaterThanEquals;

    /**
     * 创建时间戳的值大于
     */
    protected Long createTimeGreaterThan;

    /**
     * 创建时间戳的值不等于
     */
    protected Long createTimeNotEquals;

    /**
     * 创建时间戳的值包含在
     */
    protected Set<Long> createTimeIn;

    /**
     * 创建时间戳的值不包含在
     */
    protected Set<Long> createTimeNotIn;

    /**
     * 创建时间戳的值介于开始
     * mysql between是包含边界值
     */
    protected Long createTimeBetweenStart;

    /**
     * 创建时间戳的值介于结束
     * mysql between是包含边界值
     */
    protected Long createTimeBetweenEnd;

    // ********** 最后修改时间戳 modifyTime **********
    /**
     * 最后修改时间戳的值小于
     */
    protected Long modifyTimeLessThan;

    /**
     * 最后修改时间戳的值小于等于
     */
    protected Long modifyTimeLessThanEquals;

    /**
     * 最后修改时间戳的值等于
     */
    protected Long modifyTimeEquals;

    /**
     * 最后修改时间戳的值大于等于
     */
    protected Long modifyTimeGreaterThanEquals;

    /**
     * 最后修改时间戳的值大于
     */
    protected Long modifyTimeGreaterThan;

    /**
     * 最后修改时间戳的值不等于
     */
    protected Long modifyTimeNotEquals;

    /**
     * 最后修改时间戳的值包含在
     */
    protected Set<Long> modifyTimeIn;

    /**
     * 最后修改时间戳的值不包含在
     */
    protected Set<Long> modifyTimeNotIn;

    /**
     * 最后修改时间戳的值介于开始
     * mysql between是包含边界值
     */
    protected Long modifyTimeBetweenStart;

    /**
     * 最后修改时间戳的值介于结束
     * mysql between是包含边界值
     */
    protected Long modifyTimeBetweenEnd;

    // ********** 是否删除 deleted **********
    /**
     * 是否删除的值等于
     */
    protected Boolean deletedEquals;

    // ********** 分页参数 **********
    /**
     * 分页页码
     * PageHelper插件的页码由1开始，所以默认值设置为1
     */
    protected int pageNum = 1;

    /**
     * 每页大小
     */
    protected int pageSize = 20;

    // ********** 排序参数 **********
    /**
     * 排序
     * id asc 或者 id desc, modify_Time desc
     */
    protected String orderBy;

    public void optimizingCriteria() {
        //optimizingIdCriteria();
    }

    public boolean isNoCriteria() {

    }

    /**
     * 如果 idGreaterThan = 2，idLessThan = 10
     * 则转换为 idBetweenStart = 3，idBetweenEnd = 9
     * <p>
     * 如果 idGreaterThanEquals = 2，idLessThan = 10
     * 则转换为 idBetweenStart = 2，idBetweenEnd = 9
     */
    protected void optimizingIdCriteria() {
        // 如果idGreaterThan的值不为null，则转化为between
        if (this.idGreaterThan != null) {
            this.idBetweenStart = this.idGreaterThan + 1;
            this.idBetweenEnd = idLessThan - 1;

            this.idGreaterThan = null;
            this.idLessThan = null;
            return;
        }

        if (this.idGreaterThanEquals != null) {
            this.idBetweenStart = this.idGreaterThanEquals;
            this.idBetweenEnd = idLessThan - 1;

        }

        this.idLessThan = idLessThan;
    }
}
