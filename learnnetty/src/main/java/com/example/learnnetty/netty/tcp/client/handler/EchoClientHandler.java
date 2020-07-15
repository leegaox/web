package com.example.learnnetty.netty.tcp.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * TCP Client Handler
 *
 * </p>
 *
 * @author gaox
 * @since 2020/4/3
 */
@Slf4j

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final ByteBuf message;

    public EchoClientHandler() {
        message = Unpooled.buffer(256);
        message.writeBytes("hello netty".getBytes(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //利用Netty提供的高低水位机制，实现客户端精准流控
        //当发送队列待发送的字节数组达到高水位时，对应的Channel就变为不可写状态
        ctx.channel().config().setWriteBufferHighWaterMark(10 * 1024 * 1024);
        log.info("EchoClientHandler --> channelActive");
//        ctx.writeAndFlush(message);
        //创建线程向客户端循环发送请求消息，模拟客户端高并发场景
        Runnable loadRunner = new Runnable() {

            @Override
            public void run() {
                String message = "Netty OOM Example";
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ByteBuf msg = null;
                final int len = message.getBytes().length;
//                while (true) {
                    //当到达高水位时，Channel 的状态被设置为不可写，通过对Channel的可写状态进行判断来决定是否发送消息。
                    if (ctx.channel().isWritable()) {
                        msg = Unpooled.wrappedBuffer(message.getBytes());
                        ctx.writeAndFlush(msg);
                    } else {
                        log.warn("The write queue is busy：" + ctx.channel().unsafe().outboundBuffer().nioBufferSize());
                    }

//                }
            }
        };
        new Thread(loadRunner, "LoadRunner-Thread").start();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("EchoClientHandler --> channelRead：" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        ctx.write(msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("EchoClientHandler --> channelRead0：" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("EchoClientHandler --> channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.info("EchoClientHandler --> exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("read idle");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("write idle");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("all idle");
            }

        }
    }
}
