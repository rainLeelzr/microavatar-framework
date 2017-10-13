package microavatar.framework.auth.status;

/**
 * 登录状态
 * tinyint
 */
public enum LoginStatus {

    ONLINE((byte) 1, "在线"),
    OFFLINE((byte) 2, "离线"),
    FAIL((byte) 3, "登录失败");

    private byte id;

    private String desc;

    LoginStatus(byte id, String desc) {
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
