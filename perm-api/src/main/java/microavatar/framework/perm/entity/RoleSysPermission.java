/***********************************************************************
 * @实体: 角色权限
 * @实体说明:
 * 角色权限关系
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class RoleSysPermission extends BaseEntity {

    /**
     * 角色id
     * 数据库类型：varchar(36)
     */
    private String roleId;

    /**
     * 权限id
     * 数据库类型：varchar(36)
     */
    private String sysPermissionId;

    public static final String ROLEID = "roleId";

    public static final String SYSPERMISSIONID = "sysPermissionId";

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSysPermissionId() {
        return sysPermissionId;
    }

    public void setSysPermissionId(String sysPermissionId) {
        this.sysPermissionId = sysPermissionId;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"roleId\":\"")
                .append(roleId).append('\"')
                .append(",\"sysPermissionId\":\"")
                .append(sysPermissionId).append('\"')
                .append('}')
                .toString();
    }
}