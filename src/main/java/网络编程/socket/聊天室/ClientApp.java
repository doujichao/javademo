package 网络编程.socket.聊天室;

import java.net.Socket;

public class ClientApp {

	public static void main(String[] args) throws Exception {
		MyWindow w = new MyWindow();
		w.setTitle("客户端");
		Socket socket=new Socket("localhost", 8888);
		new QQClient(socket,w).start();
	}
}
