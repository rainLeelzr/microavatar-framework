package microavatar.framework.core.net.tcp.coder;

import microavatar.framework.core.net.tcp.netpackage.TcpPacket;
import microavatar.framework.core.util.log.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 收到客户端的数据后，执行此类进行数据解码
 */
public class AvatarDecoder extends ByteToMessageDecoder {

    /**
     * tcp数据包的报文结构
     * ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┐
     * ╎                              header                            ╎    body    ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎   length   ╎   method   ╎  urlLength ╎     url    ╎  bodyType  ╎    body    ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎     4B     ╎     1B     ╎     4B     ╎    0~1M    ╎     1B     ╎   0~100M   ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎                            10B~106954762B(≈102M)                            ╎
     * └╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        LogUtil.getLogger().debug("收到网络包，长度：{}", in.readableBytes());
        // 一个完整的tcp包的数据长度至少为10字节
        if (in.readableBytes() < TcpPacket.MIN_LENGTH) {
            return;
        }

        in.markReaderIndex();

        // 整个包的长度（字节）
        int length = in.readInt();

        if (length < 0) {
            LogUtil.getLogger().warn("tcp数据包的length值为负数[{}], 将强制关闭tcp链接！", length);
            ctx.close();
            return;
        }

        if (length > TcpPacket.MAX_LENGTH) {
            LogUtil.getLogger().warn("tcp数据包的length值[{}]超过最大值[{}], 将强制关闭tcp链接！", length, TcpPacket.MAX_LENGTH);
            ctx.close();
            return;
        }

        if (in.readableBytes() < length - 4) {
            in.resetReaderIndex();
            return;
        }

        // method
        byte method = in.readByte();

        // url的长度（字节）
        int urlLength = in.readInt();
        if (urlLength < 0) {
            LogUtil.getLogger().warn("tcp数据包的urlLength值为负数[{}], 将强制关闭tcp链接！", urlLength);
            ctx.close();
            return;
        }

        if (urlLength > TcpPacket.MAX_URL_LENGTH) {
            LogUtil.getLogger().warn("tcp数据包的urlLength值[{}]超过最大值[{}], 将强制关闭tcp链接！", urlLength, TcpPacket.MAX_URL_LENGTH);
            ctx.close();
            return;
        }

        // 请求的url
        byte[] url = new byte[urlLength];
        if (urlLength > 0) {
            in.readBytes(url);
        }

        // body数据的格式
        byte bodyType = in.readByte();

        // body数据
        int bodyLength = length - TcpPacket.MIN_LENGTH - urlLength;
        byte[] body = new byte[bodyLength];
        if (bodyLength > 0) {
            in.readBytes(body);
        }

        // 将数据封装成一个完整的数据包
        out.add(new TcpPacket(length, method, urlLength, url, bodyType, body));
    }
}
