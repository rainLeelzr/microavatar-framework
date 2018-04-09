package microavatar.framework.core.net.tcp.netpackage.impl;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.base.protobuf.Base;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.netpackage.item.ByteArrayItem;
import microavatar.framework.core.net.tcp.netpackage.item.IntItem;
import microavatar.framework.core.net.tcp.request.Request;
import microavatar.framework.core.net.tcp.request.RequestHandler;
import microavatar.framework.core.serialization.SerializationMode;
import microavatar.framework.core.serialization.Serializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * blue请求处理器
 *
 * @author Rain
 */
@Component
@Slf4j
public class BlueRequestHandler implements RequestHandler {

    private Map<SerializationMode, Serializer<String, Object>> serializers;

    @Resource
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public boolean supports(Request request) {
        return request.getPackageData() instanceof BluePackage;
    }

    @Override
    public void handle(Request request) throws Exception {
        Package packageData = request.getPackageData();

        BluePackage httpPackage = (BluePackage) packageData;

        // 获取请求类型
        BluePackage.HttpMethodEnum httpMethod = getHttpMethod(httpPackage);

        // 获取请求url
        String url = getUrlString(httpPackage);
        if (url == null || url.length() == 0) {
            log.error("请求的url字符串为空，跳过请求！");
            // todo 发送消息给客户端
            return;
        }

        String result = "";
        switch (httpMethod) {

            case GET:
                result = doGet(url);

                break;
            case PUT:
                String putBody = getBodyStr(httpPackage);
                result = doPut(url, putBody);

                break;
            case POST:
                String postBody = getBodyStr(httpPackage);
                result = doPost(url, postBody);

                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("HttpMethodEnum 错误，请检查改值！");
        }

        log.debug("restTemplate执行结果：{}", result);

    }

    private String doPut(String url, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.PUT, formEntity, String.class);
        return resp.getBody();
    }

    private String doPost(String url, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, formEntity, String.class);
    }

    private String doGet(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    private BluePackage.HttpMethodEnum getHttpMethod(BluePackage httpPackage) {
        IntItem item = (IntItem) httpPackage.getItem(BluePackage.HTTP_METHOD_ITEM_NAME);
        return BluePackage.HttpMethodEnum.getById(item.getData());
    }

    private String getUrlString(BluePackage httpPackage) {
        String url = null;
        ByteArrayItem urlItem = (ByteArrayItem) httpPackage.getItem(BluePackage.URL_ITEM_NAME);
        byte[] urlByteArray = urlItem.getData();
        if (urlByteArray == null) {
            log.error("请求的url字节数据为null，跳过请求！");
            return null;
        }
        try {
            url = new String(urlByteArray, BluePackage.CHART_SET);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    private String getBodyStr(BluePackage httpPackage) throws Exception {
        String bodyStr = "";

        // 获取body字节数组
        ByteArrayItem bodyArrayItem = (ByteArrayItem) httpPackage.getItem(BluePackage.BODY_ITEM_NAME);
        byte[] bodyByteArray = bodyArrayItem.getData();

        // 如果body字节数组为null，则返回空字符串
        if (bodyByteArray == null) {
            return bodyStr;
        }

        // 获取body数据的类型
        SerializationMode serializationMode = getBodySerializationMode(httpPackage);

        // 根据body的类型，反序列化body字节数组
        switch (serializationMode) {
            case JSON:
            case STRING_BYTES:
                bodyStr = new String(bodyByteArray, BluePackage.CHART_SET);
                break;

            case PROTOBUF2:
                Serializer<String, Object> stringSerializer = serializers.get(serializationMode);

                // 使用baseFrame反序列化body字节数组
                Base.Frame frame = Base.Frame.parseFrom(bodyByteArray);

                String javaProtobufClassC2S = frame.getJavaProtobufClassC2S();
                if (javaProtobufClassC2S == null || javaProtobufClassC2S.length() == 0) {

                }
                ByteString payload = frame.getPayload();
                stringSerializer.prepare(javaProtobufClassC2S);
                bodyStr = stringSerializer.deserialize(payload);

                break;
            case PROTOBUF3:
                throw new RuntimeException(String.format("未实现类型为%s的反序列化方式！", serializationMode));

            default:
                throw new RuntimeException(String.format("未实现类型为%s的反序列化方式！", serializationMode));
        }

        return bodyStr;
    }

    private SerializationMode getBodySerializationMode(Package packageData) {
        SerializationMode serializationMode = null;
        IntItem bodyTypeItem = (IntItem) packageData.getItem(BluePackage.BODY_TYPE_ITEM_NAME);
        int bodyType = bodyTypeItem.getData();
        for (SerializationMode tempMode : SerializationMode.values()) {
            if (tempMode.geId() == bodyType) {
                serializationMode = tempMode;
                break;
            }
        }
        if (serializationMode == null) {
            throw new RuntimeException(String.format("给定的bodyType=%s，系统不支持该序列化类型。", bodyType));
        }
        return serializationMode;
    }

    @Resource
    public void setSerializers(List<Serializer<String, Object>> serializers) {
        if (serializers == null || serializers.isEmpty()) {
            this.serializers = Collections.emptyMap();
            return;
        }

        if (this.serializers == null) {
            this.serializers = new HashMap<>((int) Math.max(Math.ceil(serializers.size() / 0.75f), 16));
        }

        for (Serializer<String, Object> serializer : serializers) {
            this.serializers.put(serializer.getSupport(), serializer);
        }
    }
}
