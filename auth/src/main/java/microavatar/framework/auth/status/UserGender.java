package microavatar.framework.auth.status;

/**
 * 用户性别
 * tinyint
 */
public enum UserGender {

    MALE((byte) 1, "男"),
    FEMALE((byte) 2, "女"),
    OTHER((byte) 99, "其他");

    private byte id;

    private String desc;

    UserGender(byte id, String desc) {
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
