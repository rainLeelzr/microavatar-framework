package microavatar.framework.core.serialization;

public interface serialization {

    /**
     * 反序列化
     */
    <T> T deserialize(String protobufClass, Object data) throws Exception;
}
