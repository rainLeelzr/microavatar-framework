package microavatar.framework.core.net.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.channel.AvatarServerChannelInitializer;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Rain
 */
@Configuration
@Conditional(TcpServerCondition.class)
@Slf4j
public class TcpServer {

    @Resource
    private AvatarServerChannelInitializer serverChannelInitializer;

    private ExecutorService executorService;

    private boolean isShutdown = true;

    private synchronized boolean isShutdown() {
        return isShutdown;
    }

    private synchronized void setShutdown(boolean shutdown) {
        isShutdown = shutdown;
    }

    public void start() {
        if (!isShutdown()) {
            return;
        }

        setShutdown(false);

        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor(r -> new Thread("netty-starter"));
        }

        executorService.submit(() -> {
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
                log.info("tcp 服务器启动成功！hostName:{},port:{}", hostName, port);
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("tcp 服务器启动失败！{}", e.getMessage(), e);
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
                setShutdown(true);
            }
        });

    }
}
