/***********************************************************************
 * @实体: 第三方系统用户
 * @实体说明:
 * 第三方系统与本系统的绑定关系
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class ThirdPartyUser extends BaseEntity {

    /**
     * 用户主键
     * 数据库类型：varchar(36)
     */
    private String userId;

    /**
     * 登录方式
     * 数据库类型：tinyint
     *
     * @see avatar.rain.auth.status.UserLoginType
     */
    private Byte loginType;

    /**
     * 接入时间
     * 数据库类型：bigint
     */
    private Long accessTime;

    /**
     * 唯一标识
     * 数据库类型：varchar(64)
     */
    private String uniqueId;

    public static final String USERID = "userId";

    public static final String LOGINTYPE = "loginType";

    public static final String ACCESSTIME = "accessTime";

    public static final String UNIQUEID = "uniqueId";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getLoginType() {
        return loginType;
    }

    public void setLoginType(Byte loginType) {
        this.loginType = loginType;
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"userId\":\"")
                .append(userId).append('\"')
                .append(",\"loginType\":")
                .append(loginType)
                .append(",\"accessTime\":")
                .append(accessTime)
                .append(",\"uniqueId\":\"")
                .append(uniqueId).append('\"')
                .append('}')
                .toString();
    }
}