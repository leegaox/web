package com.example.learnnetty.tcp.handler.trace;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *   Netty消息读取速度性能统计
 *      针对某个Channel的消息读取速度性能统计，可以在解码ChannelHandler之前添加一个性能统计ChannelHandler，用来对读取速度进行计数
 * </p>
 *
 * @author gaox
 * @since 2020/4/13
 */
@Slf4j
public class ServiceTraceProfileServerHandler extends ChannelInboundHandlerAdapter {
    AtomicInteger totalReadBytes =new AtomicInteger(0);

    static ScheduledExecutorService kpiExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        kpiExecutorService.scheduleAtFixedRate(()->{
            int readRates =totalReadBytes.getAndSet(0);
            log.info(ctx.channel()+"-->"+"read rates : "+readRates);
        },0,100, TimeUnit.MILLISECONDS);
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int readableBytes =((ByteBuf)msg).readableBytes();
        totalReadBytes.getAndAdd(readableBytes);
        ctx.fireChannelRead(msg);
    }
}
