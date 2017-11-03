/***********************************************************************
 * @实体: 用户组织
 * @实体说明:
 * 用户组织关系
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class UserOrg extends BaseEntity {

    /**
     * 用户id
     * 数据库类型：varchar(36)
     */
    private String userId;

    /**
     * 组织id
     * 数据库类型：varchar(36)
     */
    private String orgId;

    public static final String USERID = "userId";

    public static final String ORGID = "orgId";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"userId\":\"")
                .append(userId).append('\"')
                .append(",\"orgId\":\"")
                .append(orgId).append('\"')
                .append('}')
                .toString();
    }
}