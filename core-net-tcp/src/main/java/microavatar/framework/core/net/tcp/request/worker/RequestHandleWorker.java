package microavatar.framework.core.net.tcp.request.worker;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.base.protobuf.Base;
import microavatar.framework.core.net.tcp.netpackage.HttpPackage;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.netpackage.item.ByteArrayItem;
import microavatar.framework.core.net.tcp.netpackage.item.ByteItem;
import microavatar.framework.core.net.tcp.request.ATCPRequest;
import microavatar.framework.core.serialization.SerializationMode;
import microavatar.framework.core.serialization.Serializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Rain
 */
@Slf4j
public class RequestHandleWorker extends Thread {

    private static final int MAX_QUEUE_SIZE = Math.min(1000, 1000);

    private volatile boolean running = true;

    private BlockingQueue<ATCPRequest> blockingQueue;

    /**
     * 处理完的个数
     */
    private long handledTimes = 0;

    private Serializer serializer;

    private RestTemplate restTemplate;

    /**
     * key:   zuul配置的简化后的path
     * value: serverName
     */
    private Map<String, String> serverNameMapping;

    public RequestHandleWorker(RestTemplate restTemplate,
                               Serializer serializer,
                               Map<String, String> serverNameMapping,
                               String workName) {
        super(workName);
        this.restTemplate = restTemplate;
        this.serializer = serializer;
        this.serverNameMapping = serverNameMapping;
        this.blockingQueue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            ATCPRequest request = null;
            try {
                request = this.blockingQueue.take();
                if (request != null) {
                    log.debug("开始处理tcp请求：{}", request.getPacket().toString());
                    handle(request);
                    handledTimes++;
                }
            } catch (Exception e) {
                log.error(
                        "tcp请求处理错误: {}",
                        request == null ? "request=null" :
                                request.getPacket() == null ? "request.getPacket()=null" : request.getPacket().toString(),
                        e);
            }
        }
    }

    /**
     * 将客户端的请求转换成http请求，转发给http网关，并把http网关返回的结果，发送给客户端
     */
    private void handle(ATCPRequest request) {
        Package packet = request.getPacket();

        HttpPackage httpPackage = (HttpPackage) packet;

        // 获取请求url
        String url = getUrlString(httpPackage);
        if (url == null || url.length() == 0) {
            log.error("请求的url字符串为空，跳过请求！");
            // todo 发送消息给客户端
            return;
        }

        // 将body参数转为json，从tcp包中的body部分获取解析而来
        String bodyJson = getBodyJson(httpPackage);

        /*
            /im/perm/{id}/mm/{ij}
            /im/perm/xxx-xxxx/mm/uu
         */
        log.debug("解析得到的url：{}；body: {}", url, bodyJson);
        long start = System.currentTimeMillis();
        try {
            String requestUrl = "http://" + url;
            String restResult = "";
            if (packet.getMethod() == TcpPacket.MethodEnum.GET.geId()) {
                restResult = restTemplate.getForObject(requestUrl, String.class);
            } else if (packet.getMethod() == TcpPacket.MethodEnum.POST.geId()) {
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                HttpEntity<String> formEntity = new HttpEntity<>(json, headers);
                restResult = restTemplate.postForObject(requestUrl, formEntity, String.class);
            }
            log.debug("restTemplate执行结果：{}", restResult);

            // 如果api的返回不为void，则自动封装数据返回给客户端
            if (!matchApi.isVoid()) {
                // 用什么body类型请求，则用什么类型返回
                if (packet.getBodyType() == SerializationMode.PROTOBUF2.geId()) {
                    String protobufS2C = matchApi.getProtobufS2C();
                    if (protobufS2C == null || protobufS2C.length() == 0) {
                        log.warn(
                                "请求的body类型为pb，但返回的s2c没有定义，不能自动封装数据返回给客户端！{}",
                                matchApi.getMethodName());
                    } else {
                        serializer.prepare(matchApi.getProtobufS2C());
                        byte[] bytes = (byte[]) serializer.serialize(restResult);
                        TcpPacket responsePacket = TcpPacket.buildProtoPackage(packet.getMethod(), "/" + serverName + matchUrl, bytes);
                        request.getSession().sendMessage(responsePacket);
                    }
                } else if (packet.getBodyType() == SerializationMode.JSON.geId()) {
                    TcpPacket responsePacket = TcpPacket.buildJsonPackage(packet.getMethod(), "/" + serverName + matchUrl, restResult);
                    request.getSession().sendMessage(responsePacket);
                }
            }
        } catch (Exception e) {
            log.error("执行业务方法错误！{}", packet.toString(), e);
            // todo 发送消息给客户端
            return;
        } finally {
            long costTime = System.currentTimeMillis() - start;
            log.debug(
                    "完成业务逻处理, {} 耗时={}ms",
                    packet.toString(),
                    costTime);
        }
    }

    private String getBodyJson(HttpPackage httpPackage) throws Exception {
        String bodyJson = null;

        // 获取body数据的类型
        SerializationMode serializationMode = null;
        ByteItem bodyTypeItem = (ByteItem) httpPackage.getItem(HttpPackage.BODY_TYPE);
        byte bodyType = bodyTypeItem.getData();
        for (SerializationMode tempMode : SerializationMode.values()) {
            if (tempMode.geId() == bodyType) {
                serializationMode = tempMode;
            }
        }
        if (serializationMode == null) {
            throw new RuntimeException(String.format("给定的bodyType=%s，系统不支持该序列化类型。", bodyType));
        }

        // 获取body字节数组
        ByteArrayItem bodyArrayItem = (ByteArrayItem) httpPackage.getItem(HttpPackage.BODY);
        byte[] bodyByteArray = bodyArrayItem.getData();

        // 如果数组为null，则返回null
        if (bodyByteArray == null) {
            return null;
        }

        // 根据body的类型，反序列化body字节数组
        switch (serializationMode) {
            case JSON:
                bodyJson = new String(bodyByteArray, HttpPackage.URL_CHART_SET);
                break;
            case PROTOBUF2:
                if (serializer.support(serializationMode)) {
                    // 使用baseFrame反序列化body字节数组
                    Base.Frame frame = Base.Frame.parseFrom(bodyByteArray);
                    if (frame.getHeartbeat()) {

                    }

                    String javaProtobufClassC2S = frame.getJavaProtobufClassC2S();
                    if (javaProtobufClassC2S == null || javaProtobufClassC2S.length() == 0) {

                    }
                    // 根据baseProto的javaProtobufClass内容，反序列化payload
                    serializer.prepare(deserialize);
                    serializer.deserialize(deserialize);

                }
                break;
            case PROTOBUF3:
                throw new RuntimeException(String.format("未实现类型为%s的反序列化方式！", serializationMode));
                // break;
            default:
                throw new RuntimeException(String.format("未实现类型为%s的反序列化方式！", serializationMode));

        }

        return bodyJson;
    }

    private String getUrlString(HttpPackage httpPackage) {
        String url = null;
        ByteArrayItem urlItem = (ByteArrayItem) httpPackage.getItem(HttpPackage.URL);
        byte[] urlByteArray = urlItem.getData();
        if (urlByteArray == null) {
            log.error("请求的url字节数据为null，跳过请求！");
            return null;
        }
        try {
            url = new String(urlByteArray, HttpPackage.URL_CHART_SET);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }


    /**
     * 接收一个事件请求，放入队列中
     */
    public final void acceptRequest(ATCPRequest request) {
        boolean ok = this.blockingQueue.offer(request);
        if (!ok) {
            log.error("添加请求到 请求队列 失败！");
        }
    }

    public void shutdown() {
        if (running) {
            running = false;
            //设置线程中断标志
            this.interrupt();
        }
    }

    public boolean isRunning() {
        return this.running && this.isAlive();
    }

    private boolean isInputAndOriginalMethodEqual(RequestMethod requestMethod, byte method) {
        switch (requestMethod) {
            case GET:
                return TcpPacket.MethodEnum.GET.geId() == method;
            case HEAD:
                return TcpPacket.MethodEnum.HEAD.geId() == method;
            case POST:
                return TcpPacket.MethodEnum.POST.geId() == method;
            case PUT:
                return TcpPacket.MethodEnum.PUT.geId() == method;
            case PATCH:
                return TcpPacket.MethodEnum.PATCH.geId() == method;
            case DELETE:
                return TcpPacket.MethodEnum.DELETE.geId() == method;
            case OPTIONS:
                return TcpPacket.MethodEnum.OPTIONS.geId() == method;
            case TRACE:
                return TcpPacket.MethodEnum.TRACE.geId() == method;
            default:
                return false;
        }
    }

}
