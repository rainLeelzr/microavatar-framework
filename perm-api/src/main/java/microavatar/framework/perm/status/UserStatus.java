package microavatar.framework.perm.status;

/**
 * 用户状态
 * tinyint
 */
public enum UserStatus {

    ENABLE((byte) 1, "启用"),
    NOT_ACTIVE((byte) 2, "未激活"),
    DISABLE((byte) 3, "禁用");

    private byte id;

    private String desc;

    UserStatus(byte id, String desc) {
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
