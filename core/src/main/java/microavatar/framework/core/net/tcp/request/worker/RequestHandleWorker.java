package microavatar.framework.core.net.tcp.request.worker;

import microavatar.framework.core.api.Api;
import microavatar.framework.core.api.MicroServerService;
import microavatar.framework.core.api.ServerApi;
import microavatar.framework.core.net.tcp.netpackage.TcpPacket;
import microavatar.framework.core.net.tcp.request.ATCPRequest;
import microavatar.framework.core.serialization.ProtobufSerializationManager;
import microavatar.framework.core.util.log.LogUtil;
import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class RequestHandleWorker extends Thread {

    private static final int maxQueueSize = Math.min(1000, 1000);//要设置配置

    private volatile boolean running = true;

    private BlockingQueue<ATCPRequest> blockingQueue;

    private long handledTimes = 0;//处理完的个数

    private MicroServerService microServerService;

    private ProtobufSerializationManager protobufSerializationManager;

    private RestTemplate restTemplate;

    /**
     * key:   zuul配置的简化后的path
     * value: serverName
     */
    private Map<String, String> serverNameMapping;

    public RequestHandleWorker(RestTemplate restTemplate,
                               MicroServerService microServerService,
                               ProtobufSerializationManager protobufSerializationManager,
                               Map<String, String> serverNameMapping,
                               String workName) {
        super(workName);
        this.restTemplate = restTemplate;
        this.microServerService = microServerService;
        this.protobufSerializationManager = protobufSerializationManager;
        this.serverNameMapping = serverNameMapping;
        this.blockingQueue = new ArrayBlockingQueue<>(maxQueueSize);
        this.setDaemon(true);
    }

    public void run() {
        while (running) {
            ATCPRequest request = null;
            try {
                request = this.blockingQueue.take();
                if (request != null) {
                    LogUtil.getLogger().debug("开始处理tcp请求：{}", request.getPacket().toString());
                    handle(request);
                    handledTimes++;
                }
            } catch (Exception e) {
                LogUtil.getLogger().error(
                        "tcp请求处理错误: {}",
                        request == null ? "request=null" :
                                request.getPacket() == null ? "request.getPacket()=null" : request.getPacket().toString(),
                        e);
            }
        }
    }

    private void handle(ATCPRequest request) {
        TcpPacket packet = request.getPacket();
        String url = packet.getUrlStr();

        if (url == null || url.length() == 0) {
            LogUtil.getLogger().debug("url为空，跳过请求的处理");
            // todo 发送消息给客户端
            return;
        }

        // 从url中获取微服务的名称
        int first = url.indexOf("/");
        if (first < 0) {
            LogUtil.getLogger().debug("url错误，没有包含/，跳过请求的处理: {}", url);
            // todo 发送消息给客户端
            return;
        }

        int second = url.indexOf("/", first + 1);
        if (second < 0) {
            LogUtil.getLogger().debug("url错误，没有包含两个/，跳过请求的处理: {}", url);
            // todo 发送消息给客户端
            return;
        }

        String serverName = url.substring(first + 1, second);
        String requestUrlWithoutServerName = url.substring(second);

        // 获取微服务的api
        ServerApi serverApi = microServerService.getMicroServerByServerName(serverName);
        if (serverApi == null) {
            String path = serverNameMapping.get(serverName);
            if (path != null) {
                url = path + requestUrlWithoutServerName;
                serverApi = microServerService.getMicroServerByServerName(path.toUpperCase());
            }
        }

        if (serverApi == null) {
            LogUtil.getLogger().error(
                    "客户端[{}]请求不存在microServer的api！请检查url是否正确。{}",
                    request.getSession().getRemoteIP(),
                    request.getPacket().toString());
            // todo 发送消息给客户端
            return;
        }

        /*
            /im/user/{id}/mm/{ij}
            /im/user/xxx-xxxx/mm/uu
         */
        String[] split = requestUrlWithoutServerName.split("/");

        // 成功匹配到的api
        String matchUrl = "";
        Api matchApi = null;

        for (Map.Entry<String, List<Api>> entry : serverApi.getRequestMappingApis().entrySet()) {
            List<Api> apisInUrl = entry.getValue();

            boolean isMatchUrl = false;
            boolean isMatchMethod = false;
            Api acceptAllMethodApi = null;

            for (Api tempApi : apisInUrl) {
                String[] urlDivisions = tempApi.getUrlDivisions();
                // 如果客户端请求的url和api的url在分割部分的数组长度不一致，则肯定不匹配
                if (urlDivisions.length != split.length) {
                    continue;
                }

                boolean isMatchUrlDivision = isMatchUrlDivision(split, urlDivisions);

                // 如果url分段能匹配，则判断method是否匹配
                if (isMatchUrlDivision) {
                    isMatchUrl = true;

                    // 判断并记录此api是否接受所有method
                    if (tempApi.getRequestMethods().length == 0) {
                        acceptAllMethodApi = tempApi;
                    }

                    // 判断输入的method是否存在于原始的可以接受的method中
                    for (RequestMethod requestMethod : tempApi.getRequestMethods()) {
                        if (isInputAndOriginalMethodEqual(requestMethod, packet.getMethod())) {
                            isMatchMethod = true;
                            break;
                        }
                    }

                    // 如果method也匹配，则退出apisInUrl的循环
                    if (isMatchMethod) {
                        matchApi = tempApi;
                        matchUrl = entry.getKey();
                        break;
                    }
                }
            }

            // 如果已经找到匹配的api，则退出serverApi循环
            if (matchApi != null) {
                break;
            }

            // 如果已经匹配了url，则判断有没有接受所有method的api
            if (isMatchUrl && acceptAllMethodApi != null) {
                matchApi = acceptAllMethodApi;
                matchUrl = entry.getKey();
                break;
            }

        }

        if (matchApi == null) {
            LogUtil.getLogger().error(
                    "客户端[{}]请求不存在url的api！请检查url是否正确。{}",
                    request.getSession().getRemoteIP(),
                    request.getPacket().toString());
            // todo 发送消息给客户端
            return;
        }

        // 将body参数转为json，从tcp包中的body部分获取解析而来
        String json = parseApiParameters(packet, matchApi);
        if (json == null) {
            LogUtil.getLogger().error(
                    "解析body参数，转换为json失败[{}]{}",
                    request.getSession().getRemoteIP(),
                    request.getPacket().toString());
            // todo 发送消息给客户端
            return;
        }
        LogUtil.getLogger().debug("解析body得到的请求参数：{}", json);
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
            LogUtil.getLogger().debug("restTemplate执行结果：{}", restResult);

            // 如果api的返回不为void，则自动封装数据返回给客户端
            if (!matchApi.isVoid()) {
                // 用什么body类型请求，则用什么类型返回
                if (packet.getBodyType() == TcpPacket.BodyTypeEnum.PROTOBUF.geId()) {
                    String protobufS2C = matchApi.getProtobufS2C();
                    if (protobufS2C == null || protobufS2C.length() == 0) {
                        LogUtil.getLogger().warn(
                                "请求的body类型为pb，但返回的s2c没有定义，不能自动封装数据返回给客户端！{}",
                                matchApi.getMethodName());
                    } else {
                        byte[] bytes = protobufSerializationManager.serialize(matchApi.getProtobufS2C(), restResult);
                        TcpPacket responsePacket = TcpPacket.buildProtoPackage(packet.getMethod(), "/" + serverName + matchUrl, bytes);
                        request.getSession().sendMessage(responsePacket);
                    }
                } else if (packet.getBodyType() == TcpPacket.BodyTypeEnum.JSON.geId()) {
                    TcpPacket responsePacket = TcpPacket.buildJsonPackage(packet.getMethod(), "/" + serverName + matchUrl, restResult);
                    request.getSession().sendMessage(responsePacket);
                }
            }
        } catch (Exception e) {
            LogUtil.getLogger().error("执行业务方法错误！{}", packet.toString(), e);
            // todo 发送消息给客户端
            return;
        } finally {
            long costTime = System.currentTimeMillis() - start;
            LogUtil.getLogger().debug(
                    "完成业务逻处理, {} 耗时={}ms",
                    packet.toString(),
                    costTime);
        }
    }

    /**
     * 判断输入的url分段和原始的url分段是否匹配
     */
    private boolean isMatchUrlDivision(String[] inputDivisions, String[] originalDivisions) {
        boolean isMatchUrlDivision = true;

        // 遍历分割的每个部分
        for (int i = 0; i < originalDivisions.length; i++) {
            String u = originalDivisions[i];

            // 如果是占位符，则此段url视为正确匹配
            if (u.startsWith("{") && u.endsWith("}")) {
                continue;
            }

            // 如果不是占位符，则此段url必须要全部相同
            String requestU = inputDivisions[i];
            if (!u.equals(requestU)) {
                isMatchUrlDivision = false;
                break;
            }
        }
        return isMatchUrlDivision;
    }

    /**
     * 将客户端传递过来的参数，封装成api需要的参数类型
     */
    private String parseApiParameters(TcpPacket packet, Api api) {
        String json = "";

        // 如果不是post请求，则不解析body
        if (packet.getMethod() != TcpPacket.MethodEnum.POST.geId()) {
            return json;
        }

        // 反序列化请求参数
        byte bodyType = packet.getBodyType();
        if (bodyType == TcpPacket.BodyTypeEnum.PROTOBUF.geId()) {
            json = parseApiParametersFromProtobuf(packet, api);
        } else if (bodyType == TcpPacket.BodyTypeEnum.JSON.geId()) {
            json = parseApiParametersFromJson(packet, api);
        } else {
            LogUtil.getLogger().error("不支持tcp头信息中的包类型为[{}]的值: {}", packet.toString());
            return null;
            // todo 发送消息给客户端
        }
        return json;
    }

    /**
     * 将json格式的二进制数据封装成api需要的参数类型
     */
    private String parseApiParametersFromJson(TcpPacket packet, Api api) {
        String jsonStr;
        try {
            jsonStr = new String(packet.getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            return null;
        }
        return jsonStr;
    }

    /**
     * 将proto格式的二进制数据封装成api需要的参数类型
     */
    private String parseApiParametersFromProtobuf(TcpPacket packet, Api api) {
        String protobufC2S = api.getProtobufC2S();
        if (protobufC2S == null || protobufC2S.length() == 0) {
            LogUtil.getLogger().debug("api中无c2s信息，无需转换proto");
            return "";
        }

        GeneratedMessage protobufJavaBean;
        try {
            protobufJavaBean = protobufSerializationManager.deserialize(api.getProtobufC2S(), packet.getBody());
        } catch (Exception e) {
            LogUtil.getLogger().error(
                    "反序列化protobuf格式的request Body失败: {}",
                    packet.toString(),
                    e);
            // todo 发送消息给客户端
            return null;
        }

        LogUtil.getLogger().debug("proto内容：{}", protobufJavaBean.toString());
        String jsonStr = JsonFormat.printToString(protobufJavaBean);
        LogUtil.getLogger().debug("proto转成json后的内容：{}", jsonStr);

        return jsonStr;
    }

    /**
     * 接收一个事件请求，放入队列中
     */
    public final void acceptRequest(ATCPRequest request) {
        boolean ok = this.blockingQueue.offer(request);
        if (!ok) {
            LogUtil.getLogger().error("添加请求到 请求队列 失败！");
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
