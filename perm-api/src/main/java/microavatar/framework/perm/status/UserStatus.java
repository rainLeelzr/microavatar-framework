package microavatar.framework.perm.status;

/**
 * 用户状态
 * tinyint
 */
public enum UserStatus {

    NOT_ACTIVE((byte) 1, "未激活"),
    ENABLE((byte) 2, "启用"),
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
