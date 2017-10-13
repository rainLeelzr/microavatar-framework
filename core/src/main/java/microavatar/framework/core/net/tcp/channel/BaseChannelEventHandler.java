package microavatar.framework.core.net.tcp.channel;

import microavatar.framework.core.util.log.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BaseChannelEventHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!"远程主机强迫关闭了一个现有的连接。".equals(cause.getMessage())) {
            LogUtil.getLogger().error(cause.getMessage(), cause);
        }
        if (cause instanceof IOException) {
            return;
        }

        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object var) throws Exception {
        super.userEventTriggered(ctx, var);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }


    private String getMessage(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return new String(bytes);
    }

    private ByteBuf sendMessage(String str) {
        try {
            byte[] req = str.getBytes("UTF-8");
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeBytes(req);
            return byteBuf;
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
        }
        return null;
    }
}
