package com.example.learnnetty.netty.tcp.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 业务路由转发Handler
 * </p>
 *
 * @author gaox
 * @since 2020/4/7
 */
public class RouterServerHandler extends SimpleChannelInboundHandler<ByteBuf> {


    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf reqMsg = (ByteBuf) msg;
        byte[] body = new byte[reqMsg.readableBytes()];
        executorService.execute(() -> {
            //TODO... 解析请求消息，做路由转发

            //转发成功返回响应给客户端
            ByteBuf respMsg = ctx.alloc().heapBuffer(body.length);
            respMsg.writeBytes(body);//作为示例，简化处理，将请求返回
            ctx.writeAndFlush(respMsg);
        });
    }
}
