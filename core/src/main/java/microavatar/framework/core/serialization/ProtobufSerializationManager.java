package microavatar.framework.core.serialization;

import microavatar.framework.core.api.Api;
import microavatar.framework.core.api.MicroServerService;
import microavatar.framework.core.api.MicroServerUpdatedEvent;
import microavatar.framework.core.api.ServerApi;
import microavatar.framework.core.net.tcp.TcpServerCondition;
import microavatar.framework.core.util.log.LogUtil;
import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Conditional(TcpServerCondition.class)
public class ProtobufSerializationManager implements serialization, ApplicationListener<MicroServerUpdatedEvent> {

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
    private MicroServerService microServerService;

    /**
     * 需要在apiManager.init()之的，才执行。因为apiManager.init()未执行前，apiManager.getApis()为空
     */
    public void init() {
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
                            LogUtil.getLogger().info("加载到protoC2S：{}", protobufC2S);
                            tempMethods.put(protobufC2S, parseFrom);
                        } catch (ClassNotFoundException e) {
                            LogUtil.getLogger().error("找不到api定义的protobufC2S类：{}", api);
                        } catch (NoSuchMethodException e) {
                            LogUtil.getLogger().error("api定义的protobufC2S类中找不到parseFrom方法：{}", api);
                        }
                    }

                    String protobufS2C = api.getProtobufS2C();
                    if (protobufS2C != null && protobufS2C.length() != 0 && !tempS2CBuilderMethods.containsKey(protobufS2C)) {
                        try {
                            Class<?> protoClass = Class.forName(protobufS2C);
                            Method builder = protoClass.getMethod("newBuilder");
                            LogUtil.getLogger().info("加载到protoS2C：{}", protobufS2C);
                            tempS2CBuilderMethods.put(protobufS2C, builder);
                        } catch (Exception e) {
                            LogUtil.getLogger().error("加载api定义的protobufS2C[{}]的newBuilder方法错误", api, e);
                        }
                    }
                }
            }
        }

        this.c2sMethods = tempMethods;
        this.s2cBuilderMethods = tempS2CBuilderMethods;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(String protobufClass, Object data) throws Exception {
        Method parseMethod = getParseMethod(protobufClass);
        Object protobufJavaBean = parseMethod.invoke(null, data);
        return (T) protobufJavaBean;
    }

    /**
     * 根据proto类的名称获取pb的解析二进制数据的方法
     */
    private Method getParseMethod(String protobufClass) {
        return c2sMethods.get(protobufClass);
    }

    @Override
    public void onApplicationEvent(MicroServerUpdatedEvent event) {
        init();
    }

    /**
     * 将字符串序列化成protoS2C的字节数组
     */
    public byte[] serialize(String protobufS2C, String data) {
        Method builderMethod = s2cBuilderMethods.get(protobufS2C);
        GeneratedMessage.Builder builder;
        try {
            builder = (GeneratedMessage.Builder) builderMethod.invoke(builderMethod.getDeclaringClass());
            JsonFormat.merge(data, builder);
            return builder.build().toByteArray();
        } catch (Exception e) {
            LogUtil.getLogger().error("将字符串序列化成protoS2C[{}]错误", protobufS2C, e);
            throw new RuntimeException(e);
        }
    }
}
