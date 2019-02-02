package 网络编程和IO.socket.serversocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端:port 要绑定的端口，backlog 请求队列长度，bindAddr指定服务器要半丁的ip地址
 * ServerSocket()
 * ServerSocket(int port)
 * ServerSocket(int port,int backlog)
 * ServerSocket(int port,int backlog,InetAddress bindAddr)
 *
 *
 * SO_TIMEOUT:表示接受数据时的等待超时时间
 * SO_RESUSEADDR:表示是否允许重用服务器所绑定的本地地址
 * SO_REVBUF:表示接受数据的缓冲区的大小
 *
 * setPerformancePreferences(int connectionTime,int latency,int bandwidth)
 * 		用于设定连接时间，延迟，带宽的相对重要性，可以填任意数字，以大小区分
 * @author douji
 *
 */
public class MyServerSocket {

	public static void main(String[] args) throws IOException {
		//创建服务器Socket
		ServerSocket ss=new ServerSocket(8888);
		//接受请求
		Socket socket = ss.accept();
		System.out.println("有人连接进来了");
		//得到输入流，读取客户端的请求信息
		InputStream is = socket.getInputStream();
		byte[] bys=new byte[1024];
		int len=-1;
		while((len=is.read(bys))!=-1) {
			System.out.println(new String(bys,0,len));
		}
		System.out.println("流关闭了");
		is.close();
		socket.close();
		ss.close();
	}
}
