// package microavatar.framework;
//
// import com.google.protobuf.InvalidProtocolBufferException;
// import com.google.protobuf.TextFormat;
// import io.netty.bootstrap.Bootstrap;
// import io.netty.channel.*;
// import io.netty.channel.nio.NioEventLoopGroup;
// import io.netty.channel.socket.nio.NioSocketChannel;
// import io.netty.handler.logging.LogLevel;
// import io.netty.handler.logging.LoggingHandler;
// import io.netty.handler.timeout.IdleState;
// import io.netty.handler.timeout.IdleStateEvent;
// import lombok.extern.slf4j.Slf4j;
// import microavatar.framework.core.net.tcp.coder.AvatarDecoder;
// import microavatar.framework.core.net.tcp.coder.AvatarEncoder;
// import microavatar.framework.core.net.tcp.netpackage.Package;
// import microavatar.framework.core.serialization.SerializationMode;
// import microavatar.framework.im.protobuf.IM;
//
// import java.util.concurrent.TimeUnit;
//
// @Slf4j
// public class TcpClient {
//
//     public void start(String addr, int port) throws Exception {
//         final EventLoopGroup group = new NioEventLoopGroup();
//         try {
//             Bootstrap b = new Bootstrap();
//             b.group(group)
//                     .channel(NioSocketChannel.class)
//                     .option(ChannelOption.TCP_NODELAY, true)
//                     .handler(new LoggingHandler(LogLevel.DEBUG))
//                     .handler(new ChannelInitializer() {
//                         @Override
//                         protected void initChannel(Channel ch) throws Exception {
//                             ChannelPipeline pipeline = ch.pipeline();
//                             pipeline.addLast("decoder", new AvatarDecoder());
//                             pipeline.addLast(new ChannelInboundHandlerAdapter() {
//
//                                 @Override
//                                 public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                     // 新的channel激活时，绑定channel与session的关系
//                                     Channel channel = ctx.channel();
//
//                                     log.debug("客户端接收到服务器的连接，服务器ip：{}", channel.remoteAddress());
//
//                                     super.channelRegistered(ctx);
//
//                                     new Thread(() -> {
//                                         try {
//                                             Thread.sleep(TimeUnit.SECONDS.toMillis(2));
//                                         } catch (InterruptedException e) {
//                                             log.error(e.getMessage(), e);
//                                         }
//                                         try {
//                                             for (int i = 0; i < 10; i++) {
//                                                 // TcpPacket packet = getProtobufPackage();
//                                                 // TcpPacket packet = getJsonPackage();
//                                                 Package packageData;
//                                                 if (i % 2 == 0) {
//                                                     packageData = getJsonPackage();
//                                                 } else {
//                                                     packageData = getProtobufPackage();
//                                                 }
//                                                 ChannelFuture channelFuture = channel.writeAndFlush(packageData.getByteBuf());
//                                                 channelFuture.addListener((ChannelFutureListener) future -> {
//                                                     log.debug("发送给服务器成功：{}", packageData.toString());
//                                                 });
//                                             }
//                                         } catch (Exception e) {
//                                             e.printStackTrace();
//                                         }
//                                     }).start();
//
//                                 }
//
//                                 @Override
//                                 public void channelRead(ChannelHandlerContext cx, Object object) {
//                                     Package packet = (Package) object;
//
//                                     if (packet.getBodyType() == SerializationMode.JSON.geId()) {
//                                         log.debug("收到服务端消息：{}{}", packet.toString(), packet.getBodyStr());
//                                     } else {
//                                         try {
//                                             IM.SendTextToUserS2C sendTextToUserS2C = IM.SendTextToUserS2C.parseFrom(packet.getBody());
//                                             log.debug("收到服务端消息：{}{}", packet.toString(), TextFormat.shortDebugString(sendTextToUserS2C));
//                                         } catch (InvalidProtocolBufferException e) {
//                                             e.printStackTrace();
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void channelReadComplete(ChannelHandlerContext ctx) {
//                                     ctx.flush();
//                                 }
//
//                                 @Override
//                                 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//                                     if (!"远程主机强迫关闭了一个现有的连接。".equals(cause.getMessage())) {
//                                         log.error(cause.getMessage(), cause);
//                                     }
//                                     if (cause instanceof java.io.IOException) {
//                                         return;
//                                     }
//                                     ctx.close();
//                                 }
//
//                                 @Override
//                                 public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//                                     log.info("userEventTriggered");
//                                     super.userEventTriggered(ctx, evt);
//                                     if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
//                                         IdleStateEvent event = (IdleStateEvent) evt;
//                                         // todo 分发用户下线事件
//                                         if (event.state() == IdleState.ALL_IDLE) {
//                                             log.debug("tcp超时没有读写操作");
//                                             ctx.channel().close();
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//                                     ctx.fireChannelUnregistered();
//                                 }
//
//                                 public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//                                     super.channelInactive(ctx);
//                                     log.debug("成功关闭了一个tcp连接：{}", ctx.channel().remoteAddress());
//                                 }
//
//                             });
//                             pipeline.addLast("encoder", new AvatarEncoder());
//                         }
//                     });
//             ChannelFuture f = b.connect(addr, port).sync();
//             log.info("连接服务器成功:{},本地地址:{}", f.channel().remoteAddress(), f.channel().localAddress());
//             f.channel().closeFuture().sync();//等待客户端关闭连接
//         } catch (Exception e) {
//             log.error(e.getMessage(), e);
//         } finally {
//             group.shutdownGracefully();
//         }
//     }
//
//     private Package getProtobufPackage() {
//         IM.SendTextToUserC2S.Builder sendTextToUserC2S = IM.SendTextToUserC2S
//                 .newBuilder()
//                 .setToUserId(10)
//                 .setMessage("hello你好！")
//                 .setOpt(20);
//         byte[] bytes = sendTextToUserC2S.build().toByteArray();
//
//         TcpPacket packet = TcpPacket.buildProtoPackage(TcpPacket.MethodEnum.POST, "/im/test/hello", bytes);
//         return packet;
//     }
//
//     private Package getJsonPackage() {
//         String json = "{\"toUserId\": 10,\"message\": \"hello你好！\",\"perm\": {\"id\": \"ididid\",\"account\": \"acc\",\"pwd\": \"p\",\"createTime\": 22222,\"status\": 2}}";
//
//         TcpPacket packet = TcpPacket.buildJsonPackage(TcpPacket.MethodEnum.POST, "/im/test/hello/52", json);
//         return packet;
//     }
//
//     public static void main(String[] args) throws Exception {
//         TcpClient tcpClient = new TcpClient();
//         tcpClient.start("127.0.0.1", 8135);
//     }
//
// }
