package 网络编程.socket.聊天室;

import 网络编程.socket.聊天室.message.Message;
import 网络编程.socket.聊天室.message.QQUtil;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class QQClient extends Thread {

    private Socket socket;
    private MyWindow mwindow;

    public QQClient(Socket socket, MyWindow mwindow) {
        this.socket = socket;
        this.mwindow = mwindow;
    }

    @Override
    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            mwindow.setClientOs(os);
            InputStream is = socket.getInputStream();
            while (true){
                Message message = QQUtil.getMessageType(is);
                if(message.getmType()!=Message.TYPE_FILE){
                    byte[] contentArr = message.getmContent();
                    String str=new String(contentArr);
                    if(message.getmType()==Message.TYPE_MESSAGE){
                        System.out.println("接受到信息："+str);
                        JTextArea allmsgText = mwindow.allmsgText;
                        allmsgText.setText(allmsgText.getText()+"\r\n"+
                                str);
                    }else if(message.getmType()==Message.TYPE_FRIEND){
                        JTextArea frientList = mwindow.getFrientList();
                        frientList.setText(frientList.getText()+"\r\n"+
                                str);
                    }
                }else{
                    QQUtil.writeFile(is,message);
                }
            }
        } catch (IOException ex) {
            System.out.println("客户端异常退出！！");
        }finally {
            System.out.println("客户端正常退出!!");
        }
    }

}
