package com.example.learnnetty.tcp.server;

import com.example.learnnetty.tcp.handler.trace.ConcurrentPerformanceServerHandlerV2;
import com.example.learnnetty.tcp.handler.trace.ServiceTraceProfileServerHandler;
import com.example.learnnetty.tcp.handler.trace.ServiceTraceServerHandler;
import com.example.learnnetty.tcp.handler.trace.TrafficShapingServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Tcp Server
 * </p>
 *
 * @author gaox
 * @since 2020/4/2
 */
@Slf4j
@Service
public class TcpServer {

    private int port;

    public static void startServer() {
        //启动引导器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //服务器连接监听线程组，专门accept新的客户端client连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理每一条连接的数据的工作线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = serverBootstrap
                    //1.设置reactor线程
                    .group(bossGroup, workerGroup)
                    //2.设置nio类型的channel
                    .channel(NioServerSocketChannel.class)
                    //3.设置监听端口
                    .localAddress(18000)
                    //4.设置通道选项
                    .option(ChannelOption.SO_KEEPALIVE,true)//开启TCP底层心跳机制
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //5.装配流水线
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //有连接到达时会创建一个channel
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info("initChannel ch: " + socketChannel);
                            //修改内存申请策略为Unpooled
//                            socketChannel.config().setAllocator(UnpooledByteBufAllocator.DEFAULT);

                            ChannelPipeline p = socketChannel.pipeline();
                            //添加ChannelTrafficShapingHandler对消息读取速度进行整形限制1MB/s的速度读取请求消息
                            p.addLast("Channel Traffic Shaping",new ChannelTrafficShapingHandler(1024*1024,1024*1024,1000));
                            p.addLast(new DelimiterBasedFrameDecoder(2048*1024,Unpooled.copiedBuffer("$_".getBytes())));

                              //性能监控Handler
//                            p.addLast(new ServiceTraceProfileServerHandler());

//                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            //业务Handler
//                            p.addLast("echoHandler",new RouterServerHandler());
                            //使用Netty内置的DefaultEventExecutorGroup来并行调用业务的 ConcurrentPerformanceServerHandler，
                            //但只有一个DefaultEventExecutor线程来运行业务ChannelHandler，无法实现并行调用
//                            p.addLast(new DefaultEventExecutorGroup(100),new ConcurrentPerformanceServerHandler());

//                            p.addLast(new ConcurrentPerformanceServerHandlerV2());
                             //性能监控Handler
//                            p.addLast(new ServiceTraceServerHandler());

                            p.addLast(new StringDecoder());
                            p.addLast(new TrafficShapingServerHandler());

                        }
                    })
                    //6. 绑定server
                    .bind()
                    //7.通过同步方法阻塞直到绑定成功
                    .sync();
            //监听链路关闭Future，在监听器中释放资源
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> {
                //8.在链路关闭时释放线程池和连接句柄
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
                log.info(future.channel().toString() + "链路关闭");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        startServer();

    }
}
