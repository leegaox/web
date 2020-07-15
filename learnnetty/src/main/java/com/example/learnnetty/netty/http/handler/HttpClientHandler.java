package com.example.learnnetty.netty.http.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * </p>
 *
 * @author gaox
 * @since 2020/4/7
 */
@Slf4j
public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        log.info("HttpClientHandler --> channelRead0：" + msg.content());
        if (msg.decoderResult().isFailure()) {
            throw new Exception("Decode HttpResponse error: " + msg.decoderResult().cause());
        }


    }
}
