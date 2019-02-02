package 网络编程和IO.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiver {

	public static void main(String[] args) throws IOException {
		//实例化数据包套接字
		DatagramSocket socket=new DatagramSocket(8888);
		System.out.println("服务器地启动了！");
		byte[] buf=new byte[1024];
		DatagramPacket p=new DatagramPacket(buf, buf.length);

		try {
			while(true){
				socket.receive(p);
				int len = p.getLength();
				String str=new String(buf,0,len);
				System.out.println("收到了："+str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
