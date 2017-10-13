package microavatar.framework.core.net.tcp.channel;


import microavatar.framework.core.net.tcp.TcpServerCondition;
import microavatar.framework.core.net.tcp.coder.AvatarDecoder;
import microavatar.framework.core.net.tcp.coder.AvatarEncoder;
import microavatar.framework.core.net.tcp.request.AvatarServerRequestManager;
import microavatar.framework.core.net.tcp.session.AvatarSessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 客户端成功connect后执行此类来初始化化此channel的行为
 */
@Configuration
@Conditional(TcpServerCondition.class)
public class AvatarServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Resource
    private AvatarSessionManager avatarSessionManager;

    @Resource
    private AvatarServerRequestManager avatarServerRequestManager;

    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加解码器
        pipeline.addLast("decoder", new AvatarDecoder());

        // 设置tcp链路空闲超时时间
        int readerIdleTimeSeconds = 0;
        int writerIdleTimeSeconds = 0;
        int allIdleTimeSeconds = (int) TimeUnit.SECONDS.toSeconds(30);
        pipeline.addLast(
                "idleStateHandler",
                new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));

        // 添加事件的处理方法
        pipeline.addLast(new AvatarServerChannelEventHandler(avatarSessionManager, avatarServerRequestManager));

        // 添加编码器
        pipeline.addLast("encoder", new AvatarEncoder());
    }
}