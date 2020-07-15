package reactor;

import reactor.handler.MultiThreadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *      多Reactor多线程
 * </p>
 *
 * @author gaox
 * @since 2020/7/9
 */
public class MultiThreadEchoServerReactor{
    final ServerSocketChannel serverSocket;
    AtomicInteger next =new AtomicInteger(0);
    //选择器集合，引入多个选择器
    Selector[] selectors =new Selector[2];
    //引入多个子反应器
    SubReactor[] subReactors =null;

    MultiThreadEchoServerReactor() throws IOException {
        //初始化多个选择器
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(Config.SOCKET_SERVER_IP,Config.SOCKET_SERVER_PORT));
        //非阻塞
        serverSocket.configureBlocking(false);

        //第一个子反应器，负责监控新连接事件
        SelectionKey sk = serverSocket.register(selectors[0],SelectionKey.OP_ACCEPT);
        //绑定新连接监控handler处理器到SelectionKey
        sk.attach(new AcceptorHandler());

        //第一个子反应器，一个反应器负责一个选择器
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        //第二个子反应器，一个反应器负责一个选择器
        SubReactor subReactor2 = new SubReactor(selectors[1]);
        subReactors=new SubReactor[]{subReactor1,subReactor2};
    }

    private void startService(){
        //一个子反应器对应一个线程
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    class SubReactor implements Runnable {
      //每个线程负责一个选择器的查询和选择
      final Selector selector;

      public SubReactor(Selector selector){
          this.selector=selector;
      }

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
    }

    // 新连接处理器
    class AcceptorHandler implements Runnable {
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null){
                    //创建新的处理器处理数据  单线程模型
//                    new IOHandler(selector, channel);
                    //多线程模型
                    new MultiThreadHandler(selectors[next.get()],channel);
                }
            } catch (IOException ex) { /* ... */ }
            if(next.incrementAndGet() ==selectors.length){
                next.set(0);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new MultiThreadEchoServerReactor().startService();
    }
}
