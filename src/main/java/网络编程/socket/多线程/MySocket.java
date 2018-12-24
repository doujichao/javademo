package 网络编程.socket.多线程;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

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
}
