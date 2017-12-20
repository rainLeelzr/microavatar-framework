package microavatar.framework.core.serialization;

/**
 * @author Rain
 */
public interface Serializer<SOURCE, TARGET> {

    /**
     * 准备序列化与反序列化需要的扩展数据
     */
    void prepare(Object... data);

    /**
     * 序列化
     */
    TARGET serialize(SOURCE data) throws Exception;

    /**
     * 反序列化
     */
    SOURCE deserialize(TARGET data) throws Exception;

    /**
     * 获取序列化
     */
    boolean support(SerializationMode serializationMode);
}
