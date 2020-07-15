package com.example.learnnetty.netty.tcp.server.handler.trace;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author gaox
 * @since 2020/4/15
 */
@Slf4j
public class TrafficShapingServerHandler extends ChannelInboundHandlerAdapter {

    private static AtomicInteger counter = new AtomicInteger(0);


    static ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

    public TrafficShapingServerHandler() {
        //添加ChannelTrafficShapingHandler进行流量整形后，打印服务端读取请求消息的速度
        es.scheduleAtFixedRate(() -> {
            log.info("The server receive client rate is : " + counter.getAndSet(0) + "bytes/s");
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        counter.addAndGet(((String) msg).getBytes().length);
        body += "$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }
}
