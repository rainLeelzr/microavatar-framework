package microavatar.framework.core.serialization.impl;

import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.api.MicroServerSearchService;
import microavatar.framework.core.api.MicroServerUpdatedEvent;
import microavatar.framework.core.api.model.Api;
import microavatar.framework.core.api.model.ServerApi;
import microavatar.framework.core.serialization.SerializationMode;
import microavatar.framework.core.serialization.Serializer;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 序列化前：string json，序列化后：byte[]
 */
@Slf4j
// todo 改为配置文件 是否应用此工具
public class Protobuf2Serializer implements Serializer<String, byte[]>, ApplicationListener<MicroServerUpdatedEvent> {

    /**
     * key: protobufC2S的类名
     * value: protobuf的parseFrom(byte[] data)方法
     * 在初始化时，必须重新new map，因为微服务有可以删除了一些api，如果此处的map，在更新时，只用put来覆盖原值，则不能清空已被删除的api
     */
    private Map<String, Method> c2sMethods;

    /**
     * key:  protobufS2C的类名
     * value: protobuf的parseFrom(byte[] data)方法
     */
    private Map<String, Method> s2cBuilderMethods;

    @Resource
    private MicroServerSearchService microServerService;

    private ThreadLocal<String> protobufClass = new ThreadLocal<>();

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
        protobufClass.set((String) data[0]);
    }

    /**
     * 将json字符串序列化成protoS2C的字节数组
     *
     * @param jsonStr json字符串
     * @return 字节数组
     */
    @Override
    public byte[] serialize(String jsonStr) throws Exception {
        String protoClassStr = protobufClass.get();
        Method builderMethod = s2cBuilderMethods.get(protoClassStr);

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
    public String deserialize(byte[] data) throws Exception {
        String protoClassStr = protobufClass.get();
        Method parseMethod = c2sMethods.get(protoClassStr);
        GeneratedMessage protobufJavaBean = (GeneratedMessage) parseMethod.invoke(null, data);
        return JsonFormat.printToString(protobufJavaBean);
    }

    /**
     * 需要在apiManager.init()之的，才执行。因为apiManager.init()未执行前，apiManager.getApis()为空
     */
    private void init() {
        Map<String, Method> tempMethods = new HashMap<>();
        Map<String, Method> tempS2CBuilderMethods = new HashMap<>();

        Map<String, ServerApi> microServerApis = microServerService.getMicroServerApis();
        for (Map.Entry<String, ServerApi> entry : microServerApis.entrySet()) {
            ServerApi serverApi = entry.getValue();
            Map<String, List<Api>> requestMappingApis = serverApi.getRequestMappingApis();
            for (Map.Entry<String, List<Api>> apiEntry : requestMappingApis.entrySet()) {
                List<Api> apis = apiEntry.getValue();
                for (Api api : apis) {
                    String protobufC2S = api.getProtobufC2S();
                    if (protobufC2S != null && protobufC2S.length() != 0 && !tempMethods.containsKey(protobufC2S)) {
                        try {
                            Class<?> protoClass = Class.forName(protobufC2S);
                            Method parseFrom = protoClass.getMethod("parseFrom", byte[].class);
                            log.info("加载到protoC2S：{}", protobufC2S);
                            tempMethods.put(protobufC2S, parseFrom);
                        } catch (ClassNotFoundException e) {
                            log.error("找不到api定义的protobufC2S类：{}", api);
                        } catch (NoSuchMethodException e) {
                            log.error("api定义的protobufC2S类中找不到parseFrom方法：{}", api);
                        }
                    }

                    String protobufS2C = api.getProtobufS2C();
                    if (protobufS2C != null && protobufS2C.length() != 0 && !tempS2CBuilderMethods.containsKey(protobufS2C)) {
                        try {
                            Class<?> protoClass = Class.forName(protobufS2C);
                            Method builder = protoClass.getMethod("newBuilder");
                            log.info("加载到protoS2C：{}", protobufS2C);
                            tempS2CBuilderMethods.put(protobufS2C, builder);
                        } catch (Exception e) {
                            log.error("加载api定义的protobufS2C[{}]的newBuilder方法错误", api, e);
                        }
                    }
                }
            }
        }

        this.c2sMethods = tempMethods;
        this.s2cBuilderMethods = tempS2CBuilderMethods;
    }

    @Override
    public void onApplicationEvent(MicroServerUpdatedEvent event) {
        init();
    }

}
