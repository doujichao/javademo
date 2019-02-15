package 网络编程和IO.udp.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SendChannel {

    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramChannel channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        socket.bind(new InetSocketAddress(7000));
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            System.out.println("缓冲区剩下的字节数为"+buffer.remaining());
            int n = channel.send(buffer, new InetSocketAddress(
                    InetAddress.getByName("localhost"), 8000
            ));
            System.out.println("发送的字节数为"+n);
            Thread.sleep(500);
        }
    }
}
