package microavatar.framework.core.net.tcp.session;

import microavatar.framework.core.net.tcp.TcpServerCondition;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 会话管理器，一个Channel对应一个session
 */
@Configuration
@Conditional(TcpServerCondition.class)
public class AvatarSessionManager implements SessionManager<Channel> {

    private Map<Channel, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 发送给客户端时，默认使用的body数据表格
     */
    private String bodyType = "protobuf";

    @Override
    public void addSession(Channel channel, Session session) {
        sessionMap.putIfAbsent(channel, session);
    }

    @Override
    public Session removeSession(Channel o) {
        return sessionMap.remove(o);
    }

    @SuppressWarnings("unchecked")
    public <T extends Session> T getSession(Channel channel) {
        return (T) sessionMap.get(channel);
    }

    public Session getSessionByUserId(String userId) {
        Set<Channel> channels = sessionMap.keySet();
        if (channels.isEmpty()) {
            return null;
        }

        // 可能有一个用户同时登录了多台设备，则保留最近上线的设备，移除其他的设备的channel和session的对应关系。
        // todo 被移除的channel将不会再接收到业务数据，等待超时被清除。但是要考虑客户端还会保持心跳，所以不会超时的情况
        List<Session> tobeRemoveSessions = new ArrayList<>();
        Session lastSession = null;
        for (Channel channel : channels) {
            Session session = sessionMap.get(channel);

            if (!session.getUserId().equals(userId)) {
                continue;
            }

            if (lastSession == null) {
                lastSession = session;
                continue;
            }

            if (session.getCreateTime() > lastSession.getCreateTime()) {
                tobeRemoveSessions.add(lastSession);
                lastSession = session;
            } else {
                tobeRemoveSessions.add(session);
            }
        }

        tobeRemoveSessions.forEach(s -> {
            sessionMap.remove(s.getChannel());
            // todo 分发用户下线事件
        });

        return lastSession;
    }

    public List<Session> getSessionsByUserIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return sessionMap.entrySet()
                .stream()
                .filter(entry -> ids.contains(entry.getValue().getUserId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    //测试用
    public Session getOneSession() {
        Set<Channel> sessions = sessionMap.keySet();
        if (sessions != null && sessions.size() > 0) {
            for (Object o : sessions) {
                Session s = sessionMap.get(o);
                return s;
            }
        }
        return null;
    }

    public Session getSessionByIPAndPort(String ip, int port) {
        String address = String.format("/%s:%s", ip, port);
        Set<Channel> channels = sessionMap.keySet();
        for (Channel channel : channels) {
            String remote = channel.remoteAddress().toString();
            if (remote.equals(address)) {
                return sessionMap.get(channel);
            }
        }
        return null;
    }

    /**
     * 根据远程服务器名称，获取session连接
     */
    public Session getSessionByRemoteServerName(String remoteServerName) {
        if (remoteServerName == null || remoteServerName.length() == 0) {
            return null;
        }

        Set<Channel> sessions = sessionMap.keySet();
        for (Channel channel : sessions) {
            Session s = sessionMap.get(channel);
            if (remoteServerName.equals(s.getRemoteServerName())) {
                return s;
            }
        }

        return null;
    }

    public Map<Channel, Session> getSessionMap() {
        return sessionMap;
    }

    public String getBodyType() {
        return bodyType;
    }
}
