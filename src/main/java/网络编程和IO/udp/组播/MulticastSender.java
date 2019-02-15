package 网络编程和IO.udp.组播;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 * 组播发送类
 */
public class MulticastSender {
    public static void main(String[] args) throws Exception {
        InetAddress group= InetAddress.getByName("224.0.0.1");
        int port=4000;
        MulticastSocket ms=null;
        try {
            ms=new MulticastSocket(port);
            //加入组播组
            ms.joinGroup(group);
            while (true){
                String message="Hello"+new Date();
                byte[] buffer = message.getBytes();
                DatagramPacket dp=new DatagramPacket(buffer,buffer.length,group,port);
                ms.send(dp);//发送组播数据包
                System.out.println("发送数据报给"+group+":"+port);
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ms!=null){
                ms.leaveGroup(group);
                ms.close();
            }
        }
    }
}
