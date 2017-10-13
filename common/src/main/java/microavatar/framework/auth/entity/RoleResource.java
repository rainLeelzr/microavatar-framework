/***********************************************************************
 * @实体: 角色资源
 * @实体说明:
 * 角色资源关系
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.auth.entity;

import microavatar.framework.common.BaseEntity;

public class RoleResource extends BaseEntity {

    /**
     * 角色id
     * 数据库类型：varchar(36)
     */
    private String roleId;

    /**
     * 资源id
     * 数据库类型：varchar(36)
     */
    private String resourceId;

    public static final String ROLEID = "roleId";

    public static final String RESOURCEID = "resourceId";

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"roleId\":\"")
                .append(roleId).append('\"')
                .append(",\"resourceId\":\"")
                .append(resourceId).append('\"')
                .append('}')
                .toString();
    }
}