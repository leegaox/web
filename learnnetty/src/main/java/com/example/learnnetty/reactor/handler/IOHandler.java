package com.example.learnnetty.reactor.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * <p>
 *     IOHandlerIO处理器，主要是完成客户端的内容读取和回显
 * </p>
 *
 * @author gaox
 * @since 2020/7/9
 */
public class IOHandler implements Runnable {
    final SocketChannel channel;
    final SelectionKey sk;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public IOHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        // 仅仅取得选择键，稍后设置感兴趣的IO事件
        sk = channel.register(selector, 0);
        //将Handler作为callback对象绑定到sk
        sk.attach(this);
        //注册读写就绪事件
        sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        selector.wakeup();
    }

    public void run() {
        //处理输入和输出
        try {
            if (state == READING) {
                //从通道读
                int len =0;
                while((len=channel.read(byteBuffer))>0){
                    System.out.print(new String(byteBuffer.array(),0,len));
                }
                //读完后，准备开始写入通道，input切换成读取模式
                byteBuffer.flip();
                //读完后，注册write就绪时间
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后，进入发送的状态
                state =SENDING;
            } else if (state == SENDING) {
                //写入通道
                channel.write(byteBuffer);
                //写完后，准备开始从通道读，byteBuffer切换成写入模式
                byteBuffer.clear();
                //写完后，注册read就绪事件
                sk.interestOps(SelectionKey.OP_READ);
                state =READING;
            }
            //处理结束，这里不能关闭seect key 需要重复使用
            //sk.cancel();
        } catch (IOException ex) { /* ... */ }
    }

}
