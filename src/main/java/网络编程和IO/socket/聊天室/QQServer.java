package 网络编程和IO.socket.聊天室;

import 网络编程和IO.socket.聊天室.message.Message;
import 网络编程和IO.socket.聊天室.message.QQUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;

public class QQServer extends Thread{

	private MyWindow w;
	private Socket socket;
	private List<Socket> mockers;
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public QQServer(MyWindow w,Socket socket,List<Socket> mSockets) {
		this.w = w;
		this.socket =socket;
		this.mockers =mSockets;
	}


	@Override
	public void run() {
		try {
			System.out.println("服务器启动");
			InputStream is = socket.getInputStream();
			//主机名称
			String hostName = socket.getInetAddress().getHostName();
			while(true){
				JTextArea allMsgText = w.allmsgText;
				//不断的接受信息，转换为Message
				Message message = QQUtil.getMessageType(is);
				System.out.println("接受到的类型为："+message.getmType());
				System.out.println("接受的长度为："+message.getMLen());
				if(message.getmType()!=Message.TYPE_FILE){
					//服务器不会接受用户端好友列表
					if(message.getmType()==Message.TYPE_MESSAGE){
						byte[] contentArr = message.getmContent();
						String str=simpleDateFormat.format(new Date())+": ["+hostName+"]"+new String(contentArr);
						allMsgText.setText(allMsgText.getText()+"\r\n"+
								str);
						for (Socket socket:mockers){
							OutputStream os = socket.getOutputStream();
							QQUtil.sendMessage(str,new Message(),os);
						}
					}
				}else{
					QQUtil.writeFile(is,message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
