package microavatar.framework.core.net.tcp.channel;

import microavatar.framework.core.net.tcp.netpackage.TcpPacket;
import microavatar.framework.core.net.tcp.request.ATCPRequest;
import microavatar.framework.core.net.tcp.request.AvatarServerRequestManager;
import microavatar.framework.core.net.tcp.session.ATCPPSession;
import microavatar.framework.core.net.tcp.session.AvatarSessionManager;
import microavatar.framework.core.net.tcp.session.Session;
import microavatar.framework.common.util.log.LogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * channel事件被触发时，执行此类对应的事件处理方法
 */
public class AvatarServerChannelEventHandler extends BaseChannelEventHandler {

    private AvatarSessionManager sessionManager;

    private AvatarServerRequestManager requestManager;

    public AvatarServerChannelEventHandler(AvatarSessionManager sessionManager, AvatarServerRequestManager requestManager) {
        this.sessionManager = sessionManager;
        this.requestManager = requestManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 新的channel激活时，绑定channel与session的关系
        Channel channel = ctx.channel();

        Session session = sessionManager.getSession(channel);
        if (session != null) {
            return;
        }

        session = new ATCPPSession(channel, sessionManager.getBodyType());
        sessionManager.addSession(ctx.channel(), session);

        LogUtil.getLogger().debug("服务器接收到客户端的连接，客户端ip：{}", channel.remoteAddress());

        super.channelRegistered(ctx);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void channelRead(ChannelHandlerContext cx, Object object) {
        Session session = sessionManager.getSession(cx.channel());
        if (session == null) {
            LogUtil.getLogger().error("channelRead失败，channel对于的session为null");
            return;
        }

        TcpPacket packet = (TcpPacket) object;

        ATCPRequest bizRequest = new ATCPRequest(packet, session);
        requestManager.addRequest(bizRequest);

        // ReferenceCountUtil.release(byteBuf);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                Session session = sessionManager.getSession(ctx.channel());
                LogUtil.getLogger().debug("tcp超时没有读写操作，将主动关闭链接通道！userId={}", session.getUserId());
                ctx.close();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Channel channel = ctx.channel();
        if (channel != null) {
            Session session = sessionManager.removeSession(ctx.channel());

            LogUtil.getLogger().debug(
                    "成功关闭了一个tcp连接：{}, userId={}",
                    channel.remoteAddress(),
                    session == null ? null : session.getUserId());
        }

        // todo 分发用户下线事件
    }

}