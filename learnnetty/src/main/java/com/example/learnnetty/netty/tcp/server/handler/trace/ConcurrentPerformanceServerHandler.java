package com.example.learnnetty.netty.tcp.server.handler.trace;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 通过随机休眠模拟复杂的业务逻辑操作耗时，同时利用定时任务线程池周期性地统计服务端的处理性能
 * </p>
 *
 * @author gaox
 * @since 2020/4/9
 */
@Slf4j
public class ConcurrentPerformanceServerHandler extends ChannelInboundHandlerAdapter {

    AtomicInteger counter = new AtomicInteger(0);
    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int qps = counter.getAndSet(0);
            log.info("The server QPS is：" + qps);
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ((ByteBuf) msg).release();
        counter.incrementAndGet();
        //业务逻辑处理，模拟业务访问DB、缓存等。时延100ms~1000ms之间
        Random random = new Random();
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
    }
}
