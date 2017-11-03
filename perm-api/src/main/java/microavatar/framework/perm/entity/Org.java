/***********************************************************************
 * @实体: 组织
 * @实体说明:
 * 组织，包括集团公司、分公司、事业群、部门、小组、分队等所有团队性质的组织
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class Org extends BaseEntity {

    /**
     * 父id
     * 数据库类型：varchar(36)
     */
    private String parentId;

    /**
     * 名称
     * 数据库类型：varchar(64)
     */
    private String name;

    /**
     * 简称
     * 数据库类型：varchar(64)
     */
    private String abbreviation;

    /**
     * 地址
     * 数据库类型：varchar(256)
     */
    private String address;

    /**
     * 联系方式
     * 数据库类型：varchar(256)
     * 功能说明:
     * 可以写email、固话、手机等一切联系方式
     */
    private String contact;

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

    public static final String ABBREVIATION = "abbreviation";

    public static final String ADDRESS = "address";

    public static final String CONTACT = "contact";

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
                .append(",\"abbreviation\":\"")
                .append(abbreviation).append('\"')
                .append(",\"address\":\"")
                .append(address).append('\"')
                .append(",\"contact\":\"")
                .append(contact).append('\"')
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