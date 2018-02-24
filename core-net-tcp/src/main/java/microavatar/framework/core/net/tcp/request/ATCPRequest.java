package microavatar.framework.core.net.tcp.request;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.session.Session;

/**
 * 一个网络包请求就是一个系统的事件.类似一个task任务
 */
@Slf4j
public class ATCPRequest<P extends Package, S extends Session> {

    private final P packet;

    private final S session;

    public ATCPRequest(P packet, S session) {
        this.packet = packet;
        this.session = session;
    }

    public P getPacket() {
        return packet;
    }

    public S getSession() {
        return session;
    }

    /**
     * 锁定该连接，用来表明当前已有线程在处理该连接的网络包
     * 注意：该方法非线程安全
     *
     * @return true加锁成功，false加锁失败
     */
    public boolean tryLock() {
        return session.tryLock();
    }

    /**
     * 解锁连接
     */
    public void unlock() {
        session.unlock();
    }

    public void exceptionCaught(Exception e) {
        log.error("BizRequest捕获异常：{}", e.getMessage(), e);
    }
}
