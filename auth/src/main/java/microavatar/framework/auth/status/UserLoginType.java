package microavatar.framework.auth.status;

/**
 * 用户登录方式
 * tinyint
 * 如果登录方式是微信，则User.uniqueId为微信的openId
 * 如果登录方式是游客，则User.uniqueId为机器mac
 * 如果登录方式是用户名密码，则User.uniqueId暂时不填写
 */
public enum UserLoginType {

    ACCOUNT_PWD((byte) 1, "用户名密码"),
    WECHAT((byte) 2, "微信"),
    SINA_MICRO_BLOG((byte) 3, "新浪微博"),
    VISITOR((byte) 98, "游客"),
    OTHER((byte) 99, "其他");

    private byte id;

    private String desc;

    UserLoginType(byte id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public byte getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
