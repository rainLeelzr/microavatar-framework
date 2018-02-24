package microavatar.framework.core.serialization.impl;

import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.serialization.SerializationMode;
import microavatar.framework.core.serialization.Serializer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化前：string json，序列化后：byte[]
 *
 * @author Rain
 */
@Slf4j
public class Protobuf2Serializer implements Serializer<String, byte[]> {

    /**
     * key: protobufC2S的类名
     * value: protobuf的parseFrom(byte[] data)方法
     * 在初始化时，必须重新new map，因为微服务有可以删除了一些api，如果此处的map，在更新时，只用put来覆盖原值，则不能清空已被删除的api
     */
    private Map<String, Method> deserializeMethods = new ConcurrentHashMap<>(100);

    /**
     * key:  protobufS2C的类名
     * value: protobuf的parseFrom(byte[] data)方法
     */
    private Map<String, Method> serializeMethods = new ConcurrentHashMap<>(100);

    private ThreadLocal<String> protobufClassStr = new ThreadLocal<>();

    /**
     * 本序列化工具支持protobuf2的模式
     */
    @Override
    public boolean support(SerializationMode serializationMode) {
        return SerializationMode.PROTOBUF2 == serializationMode;
    }

    /**
     * 设置序列化与反序列化需要的proto名称
     */
    @Override
    public void prepare(Object... data) {
        protobufClassStr.set((String) data[0]);
    }

    /**
     * 将json字符串序列化成protoS2C的字节数组
     *
     * @param jsonStr json字符串
     * @return 字节数组
     */
    @Override
    public byte[] serialize(@NonNull String jsonStr) throws Exception {
        String protoClassStr = protobufClassStr.get();
        if (protoClassStr == null) {
            throw new NullPointerException("未设置protoClassStr");
        }

        Method builderMethod = serializeMethods.get(protoClassStr);
        if (builderMethod == null) {
            Class<?> protoClass = Class.forName(protoClassStr);
            builderMethod = protoClass.getMethod("newBuilder");
            serializeMethods.put(protoClassStr, builderMethod);
        }

        GeneratedMessage.Builder builder = (GeneratedMessage.Builder) builderMethod.invoke(builderMethod.getDeclaringClass());
        JsonFormat.merge(jsonStr, builder);
        return builder.build().toByteArray();
    }

    /**
     * 将字节数组反序列化为json字符串
     *
     * @param data 字节数组
     * @return json字符串
     */
    @Override
    public String deserialize(@NonNull byte[] data) throws Exception {
        String protoClassStr = protobufClassStr.get();
        if (protoClassStr == null) {
            throw new NullPointerException("未设置protoClassStr");
        }

        Method parseMethod = deserializeMethods.get(protoClassStr);
        if (parseMethod == null) {
            Class<?> protoClass = Class.forName(protoClassStr);
            parseMethod = protoClass.getMethod("parseFrom", byte[].class);
            deserializeMethods.put(protoClassStr, parseMethod);
        }

        GeneratedMessage protobufJavaBean = (GeneratedMessage) parseMethod.invoke(null, data);
        return JsonFormat.printToString(protobufJavaBean);
    }

}
