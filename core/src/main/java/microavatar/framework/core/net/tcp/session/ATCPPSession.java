package microavatar.framework.core.net.tcp.session;

import microavatar.framework.core.net.tcp.netpackage.TcpPacket;
import microavatar.framework.common.util.log.LogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * tcp
 */
public class ATCPPSession extends Session<Channel> {

    /**
     * 发送给客户端时，默认使用的body数据表格
     */
    private String bodyType;

    public ATCPPSession(Channel c, String bodyType) {
        super(c);
        this.bodyType = bodyType;
    }

    public void sendProtoToClient(TcpPacket.MethodEnum methodEnum, String url, byte[] bodyBytes) {
        TcpPacket packet = TcpPacket.buildProtoPackage(methodEnum, url, bodyBytes);
        getChannel().writeAndFlush(packet.getByteBuf());
    }

    public void sendJsonToClient(TcpPacket.MethodEnum methodEnum, String url, byte[] bodyBytes) {
        TcpPacket packet = TcpPacket.buildJsonPackage(methodEnum, url, bodyBytes);
        getChannel().writeAndFlush(packet.getByteBuf());
    }

    public void sendJsonToClient(TcpPacket.MethodEnum methodEnum, String url, String bodyJson) {
        TcpPacket packet = TcpPacket.buildJsonPackage(methodEnum, url, bodyJson);
        getChannel().writeAndFlush(packet.getByteBuf());
    }

    public String getClientIP() {
        return getChannel().localAddress().toString();
    }

    /**
     * 默认使用TcpPacket.MethodEnum.GET 来发请求
     */
    @Override
    public void sendMessage(String url, byte[] bodyBytes) {
        if (TcpPacket.BodyTypeEnum.PROTOBUF.toString().equalsIgnoreCase(bodyType)) {
            sendProtoToClient(TcpPacket.MethodEnum.GET, url, bodyBytes);
        } else if (TcpPacket.BodyTypeEnum.JSON.toString().equalsIgnoreCase(bodyType)) {
            sendJsonToClient(TcpPacket.MethodEnum.GET, url, bodyBytes);
        } else {
            TcpPacket.BodyTypeEnum[] values = TcpPacket.BodyTypeEnum.values();
            StringBuilder definedType = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                TcpPacket.BodyTypeEnum value = values[i];
                definedType.append(value.toString());
                if (i < values.length - 1) {
                    definedType.append("、");
                }
            }
            LogUtil.getLogger().warn(
                    "useBodyType未初始化，或不是以下内容的其中一个：[{}]，将自动采用{}来编码",
                    definedType.toString(),
                    TcpPacket.BodyTypeEnum.PROTOBUF);
            sendProtoToClient(TcpPacket.MethodEnum.GET, url, bodyBytes);
        }
    }

    public void sendMessage(TcpPacket packet) {
        ChannelFuture channelFuture = getChannel().writeAndFlush(packet.getByteBuf());
        channelFuture.addListener((ChannelFutureListener) future -> {
            LogUtil.getLogger().debug("[发送给客户端成功]{}", packet.toString());
        });
    }

    @Override
    public String getRemoteIP() {
        return getChannel().remoteAddress().toString();
    }

    public void close() {
        getChannel().close();
    }
}
