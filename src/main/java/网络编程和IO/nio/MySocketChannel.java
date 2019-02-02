package 网络编程和IO.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * ServerSocketChannel可以发生的事件：
 * SelectionKey.OP_ACCEPT:接收连接就绪事件，表示至少有一个客户连接，服务器可以接收这个连接
 *
 * SocketChannel发生的事件：
 * SelectionKey.OP_CONNECT:连接就绪事件，表示客户与服务器的连接已经就绪
 * SelectionKey.OP_READ:读就绪事件，表示输入流中已经可读数据
 * SelectionKey.OP_WRITE:写就绪事件，表示输入流中已经可写数据
 */
public class MySocketChannel {
    public static void main(String[] args) throws Exception {
     /*   //打开挑选器
        Selector selector = Selector.open();*/
        //开启socketChannel
        SocketChannel sc = SocketChannel.open();
        //创建连接地址
        InetSocketAddress addr = new InetSocketAddress("localhost", 8888);
        //非阻塞模式
        sc.configureBlocking(false);
        //连接
        sc.connect(addr);
        while (!sc.finishConnect()){
            //continue
        }

        ByteBuffer buffer=ByteBuffer.allocate(1024);

        int i=0;
        while (true){
            String str=i+"";
            System.out.println("client:"+str);
            buffer.clear();
            buffer.put(str.getBytes());
            buffer.flip();
            sc.write(buffer);
            buffer.clear();
            i++;
            Thread.sleep(500);
        }

    }
}
