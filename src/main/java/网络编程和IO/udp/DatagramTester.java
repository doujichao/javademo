package 网络编程和IO.udp;

import java.io.*;
import java.net.*;

public class DatagramTester {

    private int port=8000;
    private DatagramSocket sendSocekt;
    private DatagramSocket receiveSocket;
    private static final int MAX_LENGTH=3584;

    public DatagramTester () throws SocketException {
        sendSocekt=new DatagramSocket();
        receiveSocket=new DatagramSocket(port);
        receive.start();
        sender.start();
    }

    /**
     * 将长整形数据转化为字节数组
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] longToByte(long[] data) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        DataOutputStream dos=new DataOutputStream(baos);
        for (int i=0;i<data.length;i++){
            dos.writeLong(data[i]);
        }
        dos.close();
        return baos.toByteArray();
    }

    /**
     * 将字节数组转换为长整形数组
     * @param data
     * @return
     * @throws IOException
     */
    public static long[] byteToLong(byte[] data) throws IOException {
        long[] result=new long[data.length/8];
        ByteArrayInputStream bais=new ByteArrayInputStream(data);
        DataInputStream dis=new DataInputStream(bais);
        for (int i=0;i<data.length;i++){
            result[i]=dis.readLong();
        }
        return result;
    }

    /**
     * 发送字节数组的数据
     * @param bigData
     */
    public void send(byte[] bigData) throws IOException {
        //初始化DatagramPacket，第一次需要发送512个字节
        DatagramPacket  packet=new DatagramPacket(bigData,0,512, InetAddress.getByName("localhost"),port);
        int bytesSent=0;
        int count=0;
        while (bytesSent<bigData.length){
            sendSocekt.send(packet);
            int temLength = packet.getLength();
            //记录已经发送的字节数
            bytesSent+=temLength;
            System.out.println("SendSocket > 第"+(++count)+"此发送了"+ temLength +"个字节");
            int remain=bigData.length-bytesSent;
            int length=(remain>512)?512:remain;
            //设置每次需要发送的数据
            packet.setData(bigData,bytesSent,length);
        }
    }

    public byte[] receive() throws IOException {
        byte[] bigData=new byte[MAX_LENGTH];
        DatagramPacket packet=new DatagramPacket(bigData,0,MAX_LENGTH);
        int byteReceived=0;
        int count=0;
        long beginTime = System.currentTimeMillis();
        //如果接收到了bigData.length个字节，或者超过了5分钟，就结束循环
        while (byteReceived<bigData.length
        &&(System.currentTimeMillis()-beginTime<60000*5)){
            receiveSocket.receive(packet);
            int temLength = packet.getLength();
            System.out.println("ReceiveSocket > 第"+(++count)+"此发送了"+ temLength +"个字节");
            //接收到的字节长度
            byteReceived+=temLength;
            packet.setData(bigData,byteReceived,MAX_LENGTH-byteReceived);
        }
        return packet.getData();
    }

    public Thread sender= new Thread(() -> {
        long[] longArray=new long[MAX_LENGTH/8];
        for (int i=0;i<longArray.length;i++){
            longArray[i]=i+1;
            try {
                send(longToByte(longArray));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    });
    public Thread receive= new Thread(() -> {
        try {
            long[] longArray = byteToLong(receive());
            //打印接收到的数据
            for (int i=0;i<longArray.length;i++){
                if (i%100==0) System.out.println();
                System.out.println(longArray[i]+" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) throws SocketException {
        new DatagramTester();
    }
}
