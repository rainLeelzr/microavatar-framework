/***********************************************************************
 * @实体: 参数
 * @实体说明:
 * 参数表，存放所有系统参数和业务参数
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.base.entity;

import microavatar.framework.common.BaseEntity;

public class Param extends BaseEntity {

    /**
     * 名称
     * 数据库类型：varchar(32)
     * 功能说明:
     * 参数名
     */
    private String name;

    /**
     * 类型
     * 数据库类型：tinyint
     * 功能说明:
     * 参数类型。1：系统框架参数；2：业务系统参数
     */
    private Byte type;

    /**
     * key
     * 数据库类型：varchar(64)
     * 功能说明:
     * 参数的key
     */
    private String keyStr;

    /**
     * value
     * 数据库类型：varchar(256)
     * 功能说明:
     * 参数的值
     */
    private String valueStr;

    /**
     * 值的格式
     * 数据库类型：tinyint
     * 功能说明:
     * 值的格式
     * 1：字符串；2：整形；3：浮点型；4：布尔
     * 5：字符串数组；6：整形数组；7：浮点型数组；8：布尔数组
     */
    private Byte valueType;

    /**
     * 备注
     * 数据库类型：varchar(256)
     */
    private String remark;

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String KEYSTR = "keyStr";

    public static final String VALUESTR = "valueStr";

    public static final String VALUETYPE = "valueType";

    public static final String REMARK = "remark";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public Byte getValueType() {
        return valueType;
    }

    public void setValueType(Byte valueType) {
        this.valueType = valueType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"name\":\"")
                .append(name).append('\"')
                .append(",\"type\":")
                .append(type)
                .append(",\"keyStr\":\"")
                .append(keyStr).append('\"')
                .append(",\"valueStr\":\"")
                .append(valueStr).append('\"')
                .append(",\"valueType\":")
                .append(valueType)
                .append(",\"remark\":\"")
                .append(remark).append('\"')
                .append('}')
                .toString();
    }
}