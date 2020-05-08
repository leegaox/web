package com.example.learnnetty.tcp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *   TCP Server Handler
 * </p>
 *
 * @author gaox
 * @since 2020/4/3
 */
@Slf4j
public class EchoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf reqMsg = (ByteBuf) msg;
        log.info("EchoHandler --> channelRead: "+reqMsg.toString(CharsetUtil.UTF_8));
        ctx.write(msg);
        //防止OOM:
        //1.请求ByteBuf被申请后需要主动释放，防止OOM。
        ReferenceCountUtil.release(reqMsg);
        //2.让请求消息继续向后传，让内部的TailContext释放请求消息
        ctx.fireChannelRead(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("EchoHandler --> channelReadComplete");
//        ctx.flush();
        ctx.writeAndFlush("received...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("EchoHandler --> exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
