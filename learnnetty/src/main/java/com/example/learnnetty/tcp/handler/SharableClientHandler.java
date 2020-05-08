package com.example.learnnetty.tcp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *   @ChannelHandler.Sharable: 指定ChannelHandler全局共享，可被添加到多个ChannelPipeline。
 *   此时需要考虑并发性，保证couner线程安全。
 * </p>
 *
 * @author gaox
 * @since 2020/4/9
 */
@ChannelHandler.Sharable
public class SharableClientHandler extends SimpleChannelInboundHandler {
    //非线程安全
//    int counter =0;
    //通过原子类保证counter线程安全
    AtomicInteger counter =new AtomicInteger(0);


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf req = (ByteBuf) msg;

//        if(counter++ <100){
        if(counter.getAndIncrement()<1000){
            ctx.write(msg);
        }

    }
}
