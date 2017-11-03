package microavatar.framework.core.database;

import microavatar.framework.core.mvc.BaseEntity;

import java.util.*;

public class SqlCondition {

    private Map<String, Object> conditions = new HashMap<>();

    private static final String SELECTS_KEY = "_selects";

    private static final String SELECT_ALL_COLUMNS_KEY = "_select_all_columns";

    public SqlCondition select(String... selectFields) {
        @SuppressWarnings("unchecked")
        List<String> selects = (List<String>) conditions.computeIfAbsent(SELECTS_KEY, l -> new ArrayList<String>());

        selects.addAll(Arrays.asList(selectFields));
        return this;
    }

    public SqlCondition where(String fieldName, Object value) {
        conditions.put(fieldName, value);
        return this;
    }

    public SqlCondition where(String fieldName, String relation, Object value) {
        conditions.put(fieldName + relation, value);
        return this;
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> build() {
        /*
        如果没有包含_selects，则查询全部字段
        如果包含_selects，切_selects中含有_all_columns，则查询全部字段
        其余情况，查询_selects中包含的字段
         */
        if (!conditions.containsKey(SELECTS_KEY) ||
                (conditions.containsKey(SELECTS_KEY)
                        && ((List<String>) conditions.get(SELECTS_KEY)).contains(BaseEntity.ALL_COLUMNS))) {
            conditions.put(SELECT_ALL_COLUMNS_KEY, true);
        }
        return conditions;
    }

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
}
