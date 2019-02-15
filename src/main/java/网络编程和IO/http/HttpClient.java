package 网络编程和IO.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {

    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("www.baidu.com",80);
        StringBuffer sb=new StringBuffer("GET /index.jsp HTTP/1.1\r\n");
        sb.append("Host:www.baidu.com\r\n");
        sb.append("Accept:*/*\r\n");
        sb.append("Accept-Language:zh-cn\r\n");
        sb.append("Accept-Encoding:gzip,deflate\r\n");
        sb.append("User-Agent:Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.0)/4.0\r\n");
        sb.append("Connection:Keep-Alive\r\n");

        //发送HTTP请求
        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(sb.toString().getBytes());
        socket.shutdownOutput();

        InputStream socketIn = socket.getInputStream();
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=-1;
        while ((len=socketIn.read(buff))!=-1){
            buffer.write(buff,0,len);
        }
        System.out.println(new String(buffer.toByteArray()));

    }
}
