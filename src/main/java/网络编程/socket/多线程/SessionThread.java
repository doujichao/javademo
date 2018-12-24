package 网络编程.socket.多线程;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SessionThread extends Thread{

	private Socket socket;

	public SessionThread(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("有人连接进来了");
		//得到输入流，读取客户端的请求信息
		try {
//			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			InetAddress addr = socket.getInetAddress();
			int port = socket.getPort();
			byte[] bys=new byte[1024];
			int len=-1;
			while((len=is.read(bys))!=-1) {
				System.out.println(addr.getHostAddress()+":"+port+"\""+new String(bys,0,len)+"\"");
//				os.write(bys, 0, len);
			}
			System.out.println("流关闭了");
			is.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
