package microavatar.framework.core.net.tcp.request;

/**
 * 请求处理器
 */
public interface RequestHandler {

    /**
     * 此处理器是否支持 packageDate 类型的 Package
     */
    boolean supports(Request request);

    /**
     * 执行处理
     */
    void handle(Request request) throws Exception;
}
