package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class MySocketChannel {
    public static void main(String[] args) throws Exception {
        //打开挑选器
        Selector selector = Selector.open();
        //开启socketChannel
        SocketChannel sc = SocketChannel.open();
        //创建连接地址
        InetSocketAddress addr = new InetSocketAddress("localhost", 8888);
        //阻塞
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
