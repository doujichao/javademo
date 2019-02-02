package 网络编程和IO.socket.聊天室;

import 网络编程和IO.socket.聊天室.message.Message;
import 网络编程和IO.socket.聊天室.message.QQUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {

	private static ServerSocket qqserver;
	private static List<Socket> msockets=new ArrayList<Socket>();

	public static void main(String[] args) {
		try {
			MyWindow w = new MyWindow();
			w.setTitle("服务器端");
			qqserver = new ServerSocket(8888);
			while(true) {
				Socket socket = qqserver.accept();
				JTextArea frientList = w.getFrientList();

                msockets.add(socket);
                //设置服务器端朋友列表
                resetFriendList(frientList);


                for(Socket socket1:msockets){
                    OutputStream os = socket1.getOutputStream();
                    QQUtil.sendFriendMessage(frientList.getText(), new Message(), os);
                }


				new QQServer(w, socket, msockets).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				qqserver.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private static void resetFriendList(JTextArea frientList) {

	    for (int i=0;i<msockets.size();i++){
            Socket socket = msockets.get(i);
            if(!socket.isConnected()){
                msockets.remove(socket);
            }
        }

        for (Socket socket1 : msockets) {
            String friend = socket1.getInetAddress().getHostName();
            frientList.setText(friend + "\r\n");
        }
    }


}
