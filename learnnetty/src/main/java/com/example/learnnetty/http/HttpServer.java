package com.example.learnnetty.http;

import com.example.learnnetty.http.handler.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Http Server
 * </p>
 *
 * @author gaox
 * @since 2020/4/2
 */
@Slf4j
@Service
public class HttpServer {

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
                    .localAddress(18001)
                    //4.设置通道选项
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //5.装配流水线
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //有连接到达时会创建一个channel
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info("initChannel ch: " + socketChannel);
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            //解码request
                            p.addLast("decoder", new HttpRequestDecoder());
                            //编码response
                            p.addLast("encoder", new HttpResponseEncoder());
                            //业务Handler
                            p.addLast("ServerHandler",new HttpServerHandler());
                        }
                    })
                    //6. 绑定server
                    .bind()
                    //7.通过同步方法阻塞直到绑定成功
                    .sync();
            //监听链路关闭Future，在监听器中释放资源
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) channelFuture1 -> {
                //8.在链路关闭时释放线程池和连接句柄
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
                log.info(channelFuture1.channel().toString() + "链路关闭");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        startServer();

//        long startTime = System.nanoTime();
//        Thread t = new Thread(() -> {
//            try {
//                TimeUnit.DAYS.sleep(Long.MAX_VALUE);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Daemon-T");
//
//        t.setDaemon(true);//启动Daemon线程，无其他线程，JVM进程正常退出
////        t.setDaemon(false);//启动非Daemon线程，即使main线程执行完成，JVM进程也不会退出
//        t.start();
//        try {
//            TimeUnit.SECONDS.sleep(15);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("系统退出，程序执行" + (System.nanoTime() - startTime) / 1000 / 1000 / 1000 + "s");

    }
}
