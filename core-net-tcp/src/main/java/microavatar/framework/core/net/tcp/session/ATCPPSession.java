package microavatar.framework.core.net.tcp.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.serialization.SerializationMode;

/**
 * tcp
 */
@Slf4j
public class ATCPPSession extends Session<Channel> {

    /**
     * 发送给客户端时，默认使用的body数据表格
     */
    private String bodyType;

    public ATCPPSession(Channel c, String bodyType) {
        super(c);
        this.bodyType = bodyType;
    }

    public String getClientIP() {
        return getChannel().localAddress().toString();
    }

    /**
     * 默认使用TcpPacket.MethodEnum.GET 来发请求
     */
    @Override
    public void sendMessage(String url, byte[] bodyBytes) {
        if (SerializationMode.PROTOBUF2.toString().equalsIgnoreCase(bodyType)) {
        } else if (SerializationMode.JSON.toString().equalsIgnoreCase(bodyType)) {
        } else {
            SerializationMode[] values = SerializationMode.values();
            StringBuilder definedType = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                SerializationMode value = values[i];
                definedType.append(value.toString());
                if (i < values.length - 1) {
                    definedType.append("、");
                }
            }
            log.warn(
                    "useBodyType未初始化，或不是以下内容的其中一个：[{}]，将自动采用{}来编码",
                    definedType.toString(),
                    SerializationMode.PROTOBUF2);
        }
    }

    public void sendMessage(Package packet) {
        ChannelFuture channelFuture = getChannel().writeAndFlush(packet);
        channelFuture.addListener((ChannelFutureListener) future -> {
            log.debug("[发送给客户端成功]{}", packet.toString());
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
