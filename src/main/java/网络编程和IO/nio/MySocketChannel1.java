package 网络编程和IO.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

/**
 * 客户端SocketChannel
 */
public class MySocketChannel1 {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        ReadableByteChannel channel = Channels.newChannel(System.in);
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        while (true){
            channel.read(buffer);
            buffer.flip();
            open.write(buffer);
            buffer.clear();
        }
    }
}
