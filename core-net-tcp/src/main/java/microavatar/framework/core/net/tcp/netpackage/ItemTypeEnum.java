package microavatar.framework.core.net.tcp.netpackage;

/**
 * 报文包的选项类型枚举
 */
public enum ItemTypeEnum {

    BYTE(byte.class, Byte.SIZE, Byte.SIZE),

    SHORT(short.class, Short.SIZE, Short.SIZE),

    INT(int.class, Integer.SIZE, Integer.SIZE),

    LONG(long.class, Long.SIZE, Long.SIZE),

    FLOAT(float.class, Float.SIZE, Float.SIZE),

    DOUBLE(double.class, Double.SIZE, Double.SIZE),

    CHAR(char.class, Character.SIZE, Character.SIZE),

    BOOLEAN(boolean.class, 1, 1),

    BYTE_ARRAY(byte[].class, -1, 100 * 1024 * 1024 * 8);

    private Class clazz;

    /**
     * 占用空间，单位：位
     * 如果无固定占用空间，则设置-1
     */
    private int usageSpace;

    /**
     * 最大占用空间，单位：位。
     * 如果是固定占用空间，则默认为固定占用空间
     */
    private int maxUsageSpace;

    ItemTypeEnum(Class clazz, int usageSpace, int maxUsageSpace) {
        this.clazz = clazz;
        this.usageSpace = usageSpace;
        this.maxUsageSpace = maxUsageSpace;
    }

    public Class getClazz() {
        return clazz;
    }

    public int getUsageSpace() {
        return usageSpace;
    }

    public int getMaxUsageSpace() {
        return maxUsageSpace;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"字段类型\":\"")
                .append(clazz.getSimpleName())
                .append("\",\"固定占用空间\":")
                .append(usageSpace)
                .append(",\"最大占用空间\":")
                .append(maxUsageSpace)
                .append('}')
                .toString();
    }
}

