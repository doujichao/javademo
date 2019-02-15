package 网络编程和IO.udp.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ReceiveChannel {

    public static void main(String[] args) throws IOException, InterruptedException {
        final int ENOUGH_SIZE=1024;
        final int SMALL_SIZE=4;

        //决定阻塞或非阻塞模式
        boolean isBlocked=true;
        //表示缓冲区的大小
        int size=ENOUGH_SIZE;

        if (args.length>0){
            int opt = Integer.parseInt(args[0]);
            switch (opt){
                case 1:isBlocked=true;size=ENOUGH_SIZE;break;
                case 2:isBlocked=true;size=SMALL_SIZE;break;
                case 3:isBlocked=false;size=ENOUGH_SIZE;break;
                case 4:isBlocked=false;size=SMALL_SIZE;break;
            }
        }
        DatagramChannel channel=DatagramChannel.open();
        channel.configureBlocking(isBlocked);
        ByteBuffer buffer=ByteBuffer.allocate(size);
        DatagramSocket socket = channel.socket();
        SocketAddress localAddr=new InetSocketAddress(8000);

        socket.bind(localAddr);

        while (true){
            System.out.println("开始接收数据报");
            SocketAddress remoteAddr=channel.receive(buffer);
            if (remoteAddr==null){
                System.out.println("没有接收到数据包");
            }else {
                buffer.flip();
                System.out.println("接受到数据包的大小为："+buffer.remaining());
            }
            Thread.sleep(500);
        }

    }

}
