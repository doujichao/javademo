package 网络编程和IO.screenbroadcast;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Broadcast {

    public static void main(String[] args){

        try {
            DatagramSocket socket=new DatagramSocket(8888);
            //不断发送报文
            while (true){
                socket.send(popPacket());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将抓图数据组装成报文
     * @return
     */
    private static DatagramPacket popPacket() {
        try {
            BufferedImage image = printScreen();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(image,"jpg",baos);
            byte[] bytes = baos.toByteArray();
            DatagramPacket  packet=new DatagramPacket(bytes,bytes.length);
            packet.setSocketAddress(new InetSocketAddress("localhost",8888));
            return packet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 抓图
     */
    @Test
    private static BufferedImage printScreen(){

        try {
            Robot robot=new Robot();
            BufferedImage image = robot.createScreenCapture(
                    new Rectangle(0, 0, 1920, 1080));
            ImageIO.write(image,"jpg",new File("d:\\ss.jpg"));
            return image;
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
