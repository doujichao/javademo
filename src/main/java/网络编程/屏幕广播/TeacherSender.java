package 网络编程.屏幕广播;

import util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师端发送器
 */
public class TeacherSender {

    //数据包套接字
    private DatagramSocket socket;
    private Robot robot;
    private Rectangle screenRect;
    private InetSocketAddress address;

    public TeacherSender(String host,int port) {
        try {
            //初始化udp套接字
            socket=new DatagramSocket(8888);
            //初始化抓屏程序
            robot = new Robot();
            //初始化抓屏大小
            screenRect = new Rectangle(0, 0, 1920, 1080);
            address=new InetSocketAddress(host,port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播屏幕
     */
    public void broadcastScreen(){
        while(true){
            //每次广播一屏
            broadcastScreenOne();
        }
    }

    /**
     * 每次广播一屏
     */
    private void broadcastScreenOne() {
        try {
            //1、抓去一屏，形成BufferedImage
            BufferedImage screenImage=captureScreen();
            //2、将BufferedImage转化成字节数组
            byte[] screenData=covertScreen(screenImage);
            //3、对byte[]进行拆屏，每个小包60k+8+4+4+4
            List<DatagramPacket> packs=splitScreen(screenData);
            //4、封装成若干小包，设置小包的地址，并广播出去
            for(DatagramPacket p:packs){
                socket.send(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拆屏形成小包集合
     * 每个小包60*1024+8+4+4+4
     * 报头8--时间戳
     * 报头4--包数量
     * 报头4--包编号
     * 报头4--内容长度
     * @param screenData
     * @return
     */
    private List<DatagramPacket> splitScreen(byte[] screenData) {

        int len= screenData.length;
        int count;
        int packSize = 60 * 1024;
        int remain = len % packSize;
        if(remain ==0){
            count= len / (packSize);
        }else{
            count=len / (packSize)+1;
        }
        List<DatagramPacket> list=new ArrayList<DatagramPacket>();
        byte[] systemTime=getSystemTimeStamp();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DatagramPacket packet=null;
        for(int i=0;i<count;i++){
            //定义小包大小
            byte[] pack=new byte[60*1024+8+4+4+4];
            //填充时间戳
            System.arraycopy(systemTime,0,pack,0,8);
            //设置包数
            byte[] bytes = Util.int2Bytes(count);
            System.arraycopy(bytes,0,pack,8,4);

            //设置当前包数
            byte[] bytes1 = Util.int2Bytes(i + 1);
            System.arraycopy(bytes1,0,pack,12,4);

            //设置小包数据
            if(i!=(count-1)){
                System.arraycopy(Util.int2Bytes(packSize),0,pack,16,4);
                System.arraycopy(screenData,i*packSize,pack,20,packSize);
            }else{
                System.arraycopy(Util.int2Bytes(remain==0?packSize:remain),0,pack,16,4);
                System.arraycopy(screenData,i*packSize,pack,20,remain==0?packSize:remain);
            }
            packet=new DatagramPacket(pack,packSize);
            packet.setSocketAddress(address);
            list.add(packet);
        }
        return list;
    }

    /**
     * 获取时间戳
     * @return
     */
    private byte[] getSystemTimeStamp() {
        long l = System.currentTimeMillis();
        return Util.long2ByteArr(l);
    }

    /**
     * 转换成一屏图片
     * @param screenImage
     * @return
     */
    private byte[] covertScreen(BufferedImage screenImage) {
        try {
            ByteArrayOutputStream babs=new ByteArrayOutputStream();
            ImageIO.write(screenImage,"jpg",babs);
            return babs.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 抓屏
     * @return
     */
    private BufferedImage captureScreen() {
        return robot.createScreenCapture(screenRect);
    }

}
