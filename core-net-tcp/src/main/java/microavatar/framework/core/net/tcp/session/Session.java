package microavatar.framework.core.net.tcp.session;

import microavatar.framework.core.net.tcp.netpackage.Package;

/**
 * session，Channel是代表一个具体的链接，例如在netty中，就代表是netty的channel
 */
public abstract class Session<C> {

    /**
     * 锁状态
     */
    private volatile boolean locked = false;

    /**
     * 与客户端的链接通道
     */
    private final C channel;

    /**
     * 业务系统的userId
     */
    private String userId;

    /**
     * 远程服务器名
     */
    private String remoteServerName;

    /**
     * 创建session的时间
     */
    private long createTime;

    public Session(C channel) {
        this.channel = channel;
        createTime = System.currentTimeMillis();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRemoteServerName(String serverName) {
        this.remoteServerName = serverName;
    }

    public String getRemoteServerName() {
        return remoteServerName;
    }

    public C getChannel() {
        return channel;
    }

    public long getCreateTime() {
        return createTime;
    }

    /**
     * 锁定该连接，用来表明当前已有线程在处理该连接的网络包
     * 注意：该方法非线程安全
     *
     * @return true加锁成功，false加锁失败
     */
    public boolean tryLock() {
        return !locked && (locked = true);
    }

    /**
     * 解锁连接
     */
    public void unlock() {
        locked = false;
    }

    public abstract void sendMessage(String url, byte[] bodyBytes);

    public abstract void sendMessage(Package packet);

    public abstract String getRemoteIP();


}
