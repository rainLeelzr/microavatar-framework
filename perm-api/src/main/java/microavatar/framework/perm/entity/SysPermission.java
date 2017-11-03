/***********************************************************************
 * @实体: 系统权限
 * @实体说明:
 * 系统权限，对资源的操作权限、如对用户添加、删除、修改
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class SysPermission extends BaseEntity {

    /**
     * 系统资源id
     * 数据库类型：varchar(36)
     */
    private String sysResourceId;

    /**
     * 名称
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
     * 备注
     * 数据库类型：varchar(4096)
     */
    private String remark;

    public static final String SYSRESOURCEID = "sysResourceId";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String ORDERNUM = "orderNum";

    public static final String REMARK = "remark";

    public String getSysResourceId() {
        return sysResourceId;
    }

    public void setSysResourceId(String sysResourceId) {
        this.sysResourceId = sysResourceId;
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
                .append(",\"sysResourceId\":\"")
                .append(sysResourceId).append('\"')
                .append(",\"name\":\"")
                .append(name).append('\"')
                .append(",\"code\":\"")
                .append(code).append('\"')
                .append(",\"orderNum\":")
                .append(orderNum)
                .append(",\"remark\":\"")
                .append(remark).append('\"')
                .append('}')
                .toString();
    }
}