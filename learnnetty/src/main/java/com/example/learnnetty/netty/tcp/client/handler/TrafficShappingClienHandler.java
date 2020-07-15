package com.example.learnnetty.netty.tcp.client.handler;

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
public class TrafficShappingClienHandler extends ChannelInboundHandlerAdapter {

    private static AtomicInteger SEQ = new AtomicInteger(0);

    static final byte[] ECHO_REQ = new byte[1024 * 1024];

    static final String DELIMITER = "$_";

    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //以10MB/s的速度向服务端发送请求消息
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ByteBuf buf = null;
            for (int i = 0; i < 10; i++) {
                buf = Unpooled.copiedBuffer(ECHO_REQ, DELIMITER.getBytes());
                SEQ.getAndAdd(buf.readableBytes());
                if (ctx.channel().isWritable()) {
                    ctx.write(buf);
                }
            }
            ctx.flush();
            int counter = SEQ.getAndSet(0);
            log.info("The client send rate is : " + counter + "bytes/s");
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }
}
