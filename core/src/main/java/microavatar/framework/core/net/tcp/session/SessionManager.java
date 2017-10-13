package microavatar.framework.core.net.tcp.session;

/**
 * 会话管理器
 */
public interface SessionManager<C> {

    /**
     * 添加一个链接
     */
    void addSession(C channel, Session session);

    /**
     * 删除一个链接
     */
    Session removeSession(C channel);

}
