package com.example.learnnetty.netty.tcp.server.handler.trace;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * Netty关键性能指标统计
 * 消息发送速度性能指标
 * I/O线程池性能指标：消息队列积压的任务个数衡量I/O线程池工作负载
 * Netty发送队列积压消息数
 * </p>
 *
 * @author gaox
 * @since 2020/4/13
 */
@Slf4j
public class ServiceTraceServerHandler extends ChannelInboundHandlerAdapter {
    AtomicInteger totalSendBytes = new AtomicInteger(0);
    //I/O线程池性能指标
    static ScheduledExecutorService kpiExecutorService = Executors.newSingleThreadScheduledExecutor();
    //Netty发送队列积压消息数
    static ScheduledExecutorService writeQueExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        kpiExecutorService.scheduleAtFixedRate(() -> {
            Iterator<EventExecutor> executorGroup = ctx.executor().parent().iterator();
            while (executorGroup.hasNext()) {
                SingleThreadEventExecutor executor = (SingleThreadEventExecutor) executorGroup.next();
                int size = executor.pendingTasks();
                if (executor == ctx.executor()) {
                    log.info(ctx.channel() + " --> " + executor + "pending size in queue is : --> " + size);
                } else {
                    log.info(executor + "pending size in queue is : --> " + size);
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        writeQueExecutorService.scheduleAtFixedRate(() -> {
            long pendingSize = ctx.channel().unsafe().outboundBuffer().totalPendingWriteBytes();
            log.info(ctx.channel() + "-->" + "ChannelOutBoundBuffer`s totalPendingWriteBytes is : " + pendingSize + "bytes");
        }, 0, 100, TimeUnit.MILLISECONDS);

        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int sendBytes = ((ByteBuf) msg).readableBytes();
        ChannelFuture writeFuture = ctx.write(msg);
        //Netty的网络IO操作都是一部的，调用writeandflush方法，并不代表消息真正发送到TCP缓冲区，仅仅是一个异步的消息发送操作。
        writeFuture.addListener(future -> {
            //监听消息发送成功写入SocketChannel后进行性能统计
            totalSendBytes.getAndAdd(sendBytes);
        });
        ctx.flush();
    }
}
