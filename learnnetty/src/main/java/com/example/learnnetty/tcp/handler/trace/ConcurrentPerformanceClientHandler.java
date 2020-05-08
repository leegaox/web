package com.example.learnnetty.tcp.handler.trace;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *    模拟性能测试，在客户端和服务端之间建立一个TCP长连接，以 100 QPS的速度压测服务端，
 * </p>
 *
 * @author gaox
 * @since 2020/4/9
 */
@Slf4j
public class ConcurrentPerformanceClientHandler extends SimpleChannelInboundHandler {
    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduledExecutorService.scheduleAtFixedRate(()->{
            for(int i =0;i<100;i++){
                ByteBuf firstMessage = Unpooled.buffer(256);
                for(int k =0;k<firstMessage.capacity();k++){
                    firstMessage.writeByte((byte)i);
                }
                ctx.writeAndFlush(firstMessage);
            }
        },0,5000, TimeUnit.MILLISECONDS);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf reqMsg = (ByteBuf) msg;
        log.info("EchoHandler --> channelRead0: "+reqMsg.toString(CharsetUtil.UTF_8));
    }
}
