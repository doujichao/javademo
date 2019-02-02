package 网络编程和IO.屏幕广播;

import util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 学生端接受者线程
 */
public class StudentReceiverThread extends Thread{
    private DatagramSocket socket;
    private StudentUI ui;

    public StudentReceiverThread(int port,StudentUI ui){
        try {
            this.ui=ui;
            socket=new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buff=new byte[60*1024*20];
            DatagramPacket packet=new DatagramPacket(buff,buff.length);
            long lastTime=0;
            Map<Long,Map<Integer,byte[]>> pakes=new HashMap<Long, Map<Integer, byte[]>>();
            while (true){
                socket.receive(packet);
                //解析缓冲区
                PacketInfo packetInfo=parsePack(buff);
                long nowTime = packetInfo.getTimeStamp();
                Map<Integer,byte[]> screenData;
                if(lastTime==0){
                    lastTime=nowTime;
                    screenData=new HashMap<Integer, byte[]>();
                    pakes.put(nowTime,screenData);
                }else if(lastTime==nowTime){
                    screenData=pakes.get(nowTime);
                }else if(lastTime<nowTime){
                    lastTime=nowTime;
                    screenData=new HashMap<Integer, byte[]>();
                    pakes.put(nowTime,screenData);
                }else{
                    continue;
                }
                screenData.put(packetInfo.getIndex(),packetInfo.getData());
                int count=packetInfo.getCount();
                if(count==screenData.size()){
                    processScreen(pakes,count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processScreen(Map<Long, Map<Integer,byte[]>> pakes,int count) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            long time=0;
            for(Map.Entry<Long,Map<Integer,byte[]>> entry:pakes.entrySet()){
                time=entry.getKey();
                Map<Integer, byte[]> map = entry.getValue();
                for(int i=0;i<count;i++){
                    byte[] bytes = map.get(i+1);
                    baos.write(bytes);
                }
            }
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
            ui.refreshImage(image);
            pakes.remove(time);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PacketInfo parsePack(byte[] buff) {
        PacketInfo info=new PacketInfo();
        //timestamp
        info.setTimeStamp(Util.byteArr2Long(buff,0));
        //count
        info.setCount(Util.byte2Int(buff,8));
        //index
        info.setIndex(Util.byte2Int(buff,12));
        int len=Util.byte2Int(buff,16);
        byte[] data =new byte[len];
        System.arraycopy(buff,20,data,0,len);
        info.setData(data);
        return info;
    }
}
