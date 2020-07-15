package com.example.learnnetty.reactor;

import com.example.learnnetty.common.Config;
import com.example.learnnetty.reactor.handler.IOHandler;
import com.example.learnnetty.reactor.handler.MultiThreadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 *      单Reactor单线程
 * </p>
 *
 * @author gaox
 * @since 2020/7/9
 */
public class EchoServerReactor implements Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocket;

    EchoServerReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);
        //注册serverSocket 接收accept事件
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        //将新连接处理器作为附件，绑定到sk选择键
        sk.attach(new AcceptorHandler());
    }

    //轮询就绪IO事件，分发给handler
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext())
                {
                    //Reactor负责dispatch收到的事件
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException ex) { /* ... */ }
    }

    void dispatch(SelectionKey k) {
        Runnable handler = (Runnable) (k.attachment());
        //调用之前绑定到选择键的handler处理对象
        if (handler != null) {
            handler.run();
        }
    }

    // 新连接处理器
    class AcceptorHandler implements Runnable {
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null){
                    //创建新的处理器处理数据  单线程模型
                    new IOHandler(selector, channel);
                    //多线程模型
//                    new MultiThreadHandler(selector,channel);
                }
            } catch (IOException ex) { /* ... */ }
        }
    }

    public static void main(String[] args) throws IOException {
        //不管是在主线程还是子线程
        //在主线程中执行
        new EchoServerReactor(Config.SOCKET_SERVER_PORT).run();

        //在子线程中执行
//        new Thread(new EchoServerReactor(Config.SOCKET_SERVER_PORT)).start();
    }
}
