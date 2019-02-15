package 网络编程和IO.udp.组播;

import 网络编程和IO.http.StringContent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastReceiver {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress group=InetAddress.getByName("224.0.0.1");
        int port=4000;
        MulticastSocket ms=null;
        try {
            ms=new MulticastSocket(port);
            ms.joinGroup(group);

            byte[] buffer=new byte[8192];
            while (true){
                DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
                ms.receive(dp);
                String s = new String(dp.getData(), 0, dp.getLength());
                System.out.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ms!=null){
                    ms.leaveGroup(group);
                    ms.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
