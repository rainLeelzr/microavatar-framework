/***********************************************************************
 * @实体: 角色
 * @实体说明:
 * 角色
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.auth.entity;

import microavatar.framework.common.BaseEntity;

public class Role extends BaseEntity {

    /**
     * 父id
     * 数据库类型：varchar(36)
     */
    private String parentId;

    /**
     * 角色名
     * 数据库类型：varchar(64)
     */
    private String name;

    /**
     * 代码
     * 数据库类型：varchar(64)
     */
    private String code;

    /**
     * 顺序
     * 数据库类型：tinyint
     */
    private Byte orderNum;

    /**
     * 创建者
     * 数据库类型：varchar(36)
     */
    private String createdUserId;

    /**
     * 启用
     * 数据库类型：tinyint
     */
    private Byte enabled;

    /**
     * 备注
     * 数据库类型：varchar(4096)
     */
    private String remark;

    public static final String PARENTID = "parentId";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String ORDERNUM = "orderNum";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String ENABLED = "enabled";

    public static final String REMARK = "remark";

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Byte orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
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
                .append(",\"parentId\":\"")
                .append(parentId).append('\"')
                .append(",\"name\":\"")
                .append(name).append('\"')
                .append(",\"code\":\"")
                .append(code).append('\"')
                .append(",\"orderNum\":")
                .append(orderNum)
                .append(",\"createdUserId\":\"")
                .append(createdUserId).append('\"')
                .append(",\"enabled\":")
                .append(enabled)
                .append(",\"remark\":\"")
                .append(remark).append('\"')
                .append('}')
                .toString();
    }
}