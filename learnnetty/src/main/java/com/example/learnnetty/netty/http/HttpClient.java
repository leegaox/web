package com.example.learnnetty.netty.http;

import com.example.learnnetty.netty.http.handler.HttpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * <p>
 *
 * </p>
 *
 * @author gaox
 * @since 2020/4/7
 */
public class HttpClient {

    private Channel channel;
    HttpClientHandler handler = new HttpClientHandler();

    private void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpObjectAggregator(Short.MAX_VALUE));
                        ch.pipeline().addLast(handler);
                    }
                });
        ChannelFuture f = b.connect(host, port).sync();
        channel = f.channel();
    }

}
