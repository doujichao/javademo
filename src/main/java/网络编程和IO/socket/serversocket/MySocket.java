package 网络编程和IO.socket.serversocket;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Socket的几种构造方法
 * 1、Socket()
 * 2、Socket(InetAddress address,int port)
 * 3、Socket(InetAddress address,int port,InetAddress localAddr, int localPort)
 * 4、Socket(String host,int port)
 * 5、Socket(String host,int port,InetAddress localAddr int localPort)
 *
 * TCP_NODELAY:是否立即发送数据
 * SO_RESUSEADDR:表示是否允许重用Socket绑定的本地地址
 * SO_TIMEOUT:表示接受数据时的等待超时时间
 * SO_LINGER:表示执行Socket的close方法，是否立即关闭底层socket
 * SO_SNFBUF:表示发送数据的缓冲区的大小；
 * SO_REVBUF:表示接受数据的缓冲区的大小
 * SO_KEEPALIVE:表示对于长时间处于空闲状态的Socket，是否要自动把它关闭
 * OOBINLINE:表示是否支持发送一个字节的TCP紧急数据
 *
 * Socket类4中服务类型(十六进制)
 * 使用setTrafficClass(int trafficClass)
 * 1、低成本：0x02
 * 2、高可靠：0x04
 * 3、最高吞吐量：0x08
 * 4、最小延迟：0x10
 *
 * setPerformancePreferences(int connectionTime,int latency,int bandwidth)
 * 		用于设定连接时间，延迟，带宽的相对重要性，可以填任意数字，以大小区分
 */
public class MySocket {


	public static void main(String[] args) throws IOException {
		Socket socket=new Socket("localhost", 8888);
		//得到输出流对象
		OutputStream os = socket.getOutputStream();
		Scanner sc=new Scanner(System.in);
		while(true) {
			String s = sc.nextLine();
			if("exit".equals(s)) {
				break;
			}
			os.write(s.getBytes());
		}
		sc.close();
		os.close();
		socket.close();
	}

	@Test
	public void testInetAddress() throws Exception {
		//返回主机ip
		System.out.println(InetAddress.getLocalHost());
		//返回指定ip的ip地址
		System.out.println(InetAddress.getByName("114.114.114.114"));
		//返回指定网址的ip地址
		System.out.println(InetAddress.getByName("www.baidu.com"));

	}

	/**
	 * 测试等待连接时长1分钟
	 * @throws IOException
	 */
	@Test
	public void testWaitingTime() throws IOException {
		Socket socket=new Socket();
		socket.setPerformancePreferences(1,2,3);
		socket.connect(new InetSocketAddress("localhost",8888),6000);
	}

	/**
	 * 测试本地主机上1到1024的端口是否被占用
	 */
	@Test
	public void testPortScanner(){
		String host="localhost";
		scan(host);
	}

	public void scan(String host){
		Socket socket=null;
		for (int port=1;port<1024;port++){
			try {
				socket=new Socket(host,port);
				System.out.println(socket.isClosed());
				System.out.println("There is a server on port "+port);
			} catch (Exception e) {
				System.out.println("Can't connect to port"+port);
			}finally {
				try {
					if (socket!=null) socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
