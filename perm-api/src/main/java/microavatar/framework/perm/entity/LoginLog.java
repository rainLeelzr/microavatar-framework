/***********************************************************************
 * @实体: 登录日志
 * @实体说明:
 * 只要有登录请求，无论登录成功与否，都记录
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class LoginLog extends BaseEntity {

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
     * 客户端登录ip
     * 数据库类型：varchar(16)
     */
    private String clientIp;

    /**
     * 登录时间
     * 数据库类型：bigint
     */
    private Long loginTime;

    /**
     * 登出时间
     * 数据库类型：bigint
     */
    private Long logoutTime;

    /**
     * 状态
     * 数据库类型：tinyint
     *
     * @see avatar.rain.auth.status.LoginStatus
     */
    private Byte status;

    public static final String USERID = "userId";

    public static final String LOGINTYPE = "loginType";

    public static final String CLIENTIP = "clientIp";

    public static final String LOGINTIME = "loginTime";

    public static final String LOGOUTTIME = "logoutTime";

    public static final String STATUS = "status";

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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
                .append(",\"clientIp\":\"")
                .append(clientIp).append('\"')
                .append(",\"loginTime\":")
                .append(loginTime)
                .append(",\"logoutTime\":")
                .append(logoutTime)
                .append(",\"status\":")
                .append(status)
                .append('}')
                .toString();
    }
}