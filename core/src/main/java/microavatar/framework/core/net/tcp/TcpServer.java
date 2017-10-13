package microavatar.framework.core.net.tcp;

import microavatar.framework.core.net.tcp.channel.AvatarServerChannelInitializer;
import microavatar.framework.core.util.log.LogUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Conditional(TcpServerCondition.class)
public class TcpServer {

    @Resource
    private AvatarServerChannelInitializer serverChannelInitializer;

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        String hostName = "localhost";
        int port = 8135;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true) //重用地址
                    .childOption(ChannelOption.SO_RCVBUF, 65536)
                    .childOption(ChannelOption.SO_SNDBUF, 65536)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(serverChannelInitializer);
            // handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行，这是两者的区别。

            ChannelFuture f = bootstrap.bind(port).sync();
            LogUtil.getLogger().info("tcp 服务器启动成功！hostName:{},port:{}", hostName, port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LogUtil.getLogger().error("tcp 服务器启动失败！{}", e.getMessage(), e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
