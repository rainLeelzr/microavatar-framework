package microavatar.framework.auth.model;

import microavatar.framework.auth.status.UserLoginType;

/**
 * 登录系统的用户
 */
public class LoginUser {

    /**
     * 对应的 userId
     */
    private String userId;

    /**
     * 登录方式
     *
     * @see UserLoginType
     */
    private int loginType;

    /**
     * 用户唯一id
     *
     * @see UserLoginType
     */
    private String uniqueId;

    /**
     * 客户端登录的 ip
     */
    private String clientIp;

    /**
     * 登录时间
     */
    private long loginTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }
}
