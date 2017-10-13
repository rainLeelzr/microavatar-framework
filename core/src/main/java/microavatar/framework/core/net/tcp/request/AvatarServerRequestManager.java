package microavatar.framework.core.net.tcp.request;

import microavatar.framework.core.net.tcp.TcpServerCondition;
import microavatar.framework.core.net.tcp.request.worker.RequestHandleWorkerPool;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 请求管理器
 * 根据客户端的消息命令，分发到各个指定的方法中去处理请求
 */
@Configuration
@Conditional(TcpServerCondition.class)
public class AvatarServerRequestManager {

    /**
     * 处理客户端请求的线程池
     */
    @Resource
    private RequestHandleWorkerPool requestHandleWorkerPool;

    public void addRequest(ATCPRequest bizRequest) {
        this.requestHandleWorkerPool.putRequestInQueue(bizRequest);
    }

}
