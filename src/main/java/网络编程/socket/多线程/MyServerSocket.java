package 网络编程.socket.多线程;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端
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
