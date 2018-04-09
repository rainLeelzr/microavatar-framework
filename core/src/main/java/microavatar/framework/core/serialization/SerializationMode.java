package microavatar.framework.core.serialization;

/**
 * 数据的序列化模式
 *
 * @author Rain
 */
public enum SerializationMode {

    /**
     * json模式
     */
    JSON((byte) 1),

    /**
     * protobuf2模式
     */
    PROTOBUF2((byte) 2),

    /**
     * protobuf3模式
     */
    PROTOBUF3((byte) 3),

    /**
     * String --> bytes 模式
     */
    STRING_BYTES((byte) 4);

    private byte id;

    SerializationMode(byte id) {
        this.id = id;
    }

    public byte geId() {
        return id;
    }
}
