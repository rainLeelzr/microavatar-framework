package microavatar.framework.core.net.tcp.coder;

import microavatar.framework.core.net.tcp.netpackage.TcpPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 发送数据给客户端的时，执行此类进行编码
 */
public class AvatarEncoder extends MessageToByteEncoder<TcpPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TcpPacket packet, ByteBuf out) throws Exception {
        out.writeBytes(packet.getByteBuf());
    }
}
