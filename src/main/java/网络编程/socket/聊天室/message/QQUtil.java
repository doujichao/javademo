package 网络编程.socket.聊天室.message;


import util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QQUtil {
    /**
     * 发送信息给客户端
     * @param friend 朋友项
     * @param message 报文对象
     * @param os 输出流
     * @throws IOException io异常
     */
    public static void sendFriendMessage(String friend, Message message, OutputStream os) throws IOException {
        message.setMType(Message.TYPE_FRIEND);
        byte[] friendBytes = friend.getBytes();
        message.setMLen(friendBytes.length);
        message.setMContent(friendBytes);
        os.write(message.getByteArrays());
    }

    /**
     * 发送信息给客户端
     * @param friend 朋友项
     * @param message 报文对象空对象
     * @param os 输出流
     * @throws IOException io异常
     */
    public static void sendMessage(String friend, Message message, OutputStream os) throws IOException {
        message.setMType(Message.TYPE_MESSAGE);
        byte[] friendBytes = friend.getBytes();
        message.setMLen(friendBytes.length);
        message.setMContent(friendBytes);
        os.write(message.getByteArrays());
    }


    /**
     * 根据输入流读取文件类型和长度
     * @param is 传入的输入流
     * @return 如果报文类型不是file，传回的Message中包含内容，如果是file
     * @throws IOException
     */
    public static Message getMessageType(InputStream is) throws IOException{
        Message message=new Message();
        //读取类型
        byte[] bys=new byte[5];
        is.read(bys);
        message.setMType(bys[0]);
        //读取长度
        byte[] lenArr=new byte[4];
        System.arraycopy(bys,1,lenArr,0,4);
        int len = Util.byte2Int(lenArr,0);
        message.setMLen(len);

        if(bys[0]!=Message.TYPE_FILE){
            //读取内容
            byte[] conArr=new byte[len];
            is.read(conArr);
            message.setMContent(conArr);
        }
        return message;
    }

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd");

    /**
     * 通过传入信息保存到本地
     * @param is 输入流
     * @param message 文件信息
     */
    public static void writeFile(InputStream is,Message message) throws IOException{
        int len = message.getMLen();
        FileOutputStream fos=new FileOutputStream(sdf.format(new Date())+".txt");
        int aLen=len / 1024;
        byte[] bys=new byte[1024];
        for (int i=0;i<aLen;i++){
            is.read(bys);
            fos.write(bys);
        }
        int remain=len % 1024;
        if(remain!=0){
            byte[] arr=new byte[remain];
            is.read(arr);
            fos.write(arr);
        }
    }
}
