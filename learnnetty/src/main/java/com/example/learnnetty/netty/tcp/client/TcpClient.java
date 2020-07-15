package com.example.learnnetty.netty.tcp.client;


import com.example.learnnetty.common.Config;
import com.example.learnnetty.netty.tcp.client.handler.EchoClientHandler;
import com.example.learnnetty.netty.tcp.client.handler.TrafficShappingClienHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Tcp Client
 * </p>
 *
 * @author gaox
 * @since 2020/4/2
 */
@Slf4j
public class TcpClient {

    public static void main(String[] args) throws Exception {
        initClientPool(1);
        //模拟8个客户端并发访问服务器
//        initClientPool(8);
    }

    public static void initClientPool(int size) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
//                        channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(30, 60, 100));
//                        channel.pipeline().addLast("log", new LoggingHandler());
//                        channel.pipeline().addLast(new StringEncoder());
                        channel.pipeline().addLast("echoClientHandler",new EchoClientHandler());
//                        channel.pipeline().addLast(new ConcurrentPerformanceClientHandler());

                        channel.pipeline().addLast(new TrafficShappingClienHandler());
                    }
                });
        for (int i = 0; i < size; i++) {
            final int index = i;
            //尽管bootstrap不是线程安全，但是connect方法是串行执行的且是线程安全的
            bootstrap.connect(Config.SOCKET_SERVER_IP, Config.SOCKET_SERVER_PORT).sync().channel().closeFuture().addListener(future -> {
                log.info(future.toString() + "client" + index + "链路关闭");
                group.shutdownGracefully();
            });
        }
    }

}
