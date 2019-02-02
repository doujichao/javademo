package 网络编程和IO.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 发送方
 * @author douji
 *
 */
public class UdpSend {

	public static void main(String[] args) throws IOException {
		DatagramSocket socket=new DatagramSocket(9999);
		//构造数据缓冲区，形成数据包包
		byte[] bs = "helloworld".getBytes();
		DatagramPacket packet=new DatagramPacket(bs, bs.length);
		
		packet.setSocketAddress(new InetSocketAddress("localhost",8888));
		
		socket.send(packet);
		socket.close();
	}
}
