package com.example.learnnetty.tcp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *   模拟服务端业务逻辑处理慢，NioEventLoop线程被阻塞，无法读取请求消息。
 *
 *      采用NettyI/O线程和业务线程分离的策略防挂死
 * </p>
 *
 * @author gaox
 * @since 2020/4/9
 */
@Slf4j
public class IotCarsServerHandler extends SimpleChannelInboundHandler {

    static AtomicInteger sum = new AtomicInteger(0);
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(100),
//                new ThreadPoolExecutor.CallerRunsPolicy()//由调用方线程NioEventLoop执行业务逻辑，可能导致NioEventLoop线程被阻塞，无法读取请求消息
            new ThreadPoolExecutor.DiscardPolicy()//采用丢弃策略，防止阻塞Netty的NioEventLoop线程
    );
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                executor.execute(()->{
                    //其他业务逻辑处理，访问数据库
                    if(sum.get()%100 ==0 || Thread.currentThread()==ctx.channel().eventLoop()){
                        try{
                            //访问数据库，模拟偶现的数据库慢，同步阻塞15s
                            TimeUnit.SECONDS.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ctx.writeAndFlush(msg);
                    }
                });
    }



}
