package microavatar.framework.core.database;

import org.springframework.util.Assert;

import java.util.*;

/**
 * @author rain
 */
public class SqlCondition extends HashMap<String, Object> {

    private static final String SELECTS_KEY = "_selects";

    private static final String SELECT_ALL_COLUMNS_KEY = "_select_all_columns";

    private static final String ALL_COLUMNS = "_all_columns";

    /**
     * 记录是否等待构建sql条件，如果在调用get方法时，还在等待构建sql条件，则自动调用build完成构建，并把设置为false
     * 完成构建后，不能再调用select、where方法进行sql条件的添加
     */
    private boolean waitingBuild = true;

    public SqlCondition select(String... selectFields) {
        Assert.isTrue(waitingBuild, "sql条件已完成构建，不能再添加新的条件！");

        @SuppressWarnings("unchecked")
        List<String> selects = (List<String>) this.computeIfAbsent(SELECTS_KEY, l -> new ArrayList<String>());

        selects.addAll(Arrays.asList(selectFields));
        return this;
    }

    public SqlCondition where(String fieldName, Object value) {
        Assert.isTrue(waitingBuild, "sql条件已完成构建，不能再添加新的条件！");

        this.put(fieldName, value);
        return this;
    }

    public SqlCondition where(String fieldName, String relation, Object value) {
        Assert.isTrue(waitingBuild, "sql条件已完成构建，不能再添加新的条件！");

        this.put(fieldName + relation, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> build() {
        // 如果没有包含_selects，则查询全部字段。其余情况，查询_selects中包含的字段
        if (!this.containsKey(SELECTS_KEY)) {
            this.put(SELECT_ALL_COLUMNS_KEY, true);
        }

        waitingBuild = false;

        return this;
    }


    @Override
    public Object get(Object key) {
        if (waitingBuild) {
            build();
        }

        return super.get(key);
    }

    /**
     * 等于
     */
    public static final String EQ = "_eq";

    /**
     * 大于
     */
    public static final String GT = "_gt";

    /**
     * 大于等于
     */
    public static final String GT_EQ = "_gt_eq";

    /**
     * 小于
     */
    public static final String LT = "_lt";

    /**
     * 小于等于
     */
    public static final String LT_EQ = "_lt_eq";

    /**
     * 模糊查询
     */
    public static final String LIKE = "_like";
}
