package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MySocketChannel2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        //选择器
        Selector selector = Selector.open();
        //打开通道
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        //设置阻塞模式
        channel.configureBlocking(false);
        //缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //注册通道
        channel.register(selector, SelectionKey.OP_CONNECT|SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        while (true){
            //进行挑选
            selector.select();
            Iterator<SelectionKey> iterator =
                    selector.selectedKeys().iterator();
            if (iterator.hasNext()){
                SelectionKey key = iterator.next();

                //1、connectable
                if (key.isConnectable()){
                    System.out.println("client is Connected");
                }

                //2、isReadable
                if (key.isReadable()){
                    buffer.clear();
                    channel.read(buffer);
                    buffer.flip();
                    System.out.println("client receive:"+new String(buffer.array(),0,buffer.limit()));
                }

                //3、isWritable
                if (key.isWritable()){
                    buffer.clear();
                    buffer.put("Hi,I'm client!".getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    System.out.println("client send:Hi,I'm client!");
                }
                Thread.sleep(500);
            }
        }
    }
}
