package microavatar.framework.perm.model;

import microavatar.framework.perm.status.UserLoginType;

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

}
