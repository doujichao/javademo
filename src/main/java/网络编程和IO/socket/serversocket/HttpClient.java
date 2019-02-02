package 网络编程和IO.socket.serversocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {

    String host="www.javathinker.org";
    int port=80;
    Socket socket;

    public void createSocket() throws IOException {
        socket=new Socket(host,port);
    }

    public void communicate() throws Exception{
        StringBuffer sb=new StringBuffer("GET "+"/index.jsp HTTP/1.1\r\n");
        sb.append("Host:"+host+"\r\n");
        sb.append("Accept:*/*\r\n");
        sb.append("Accept-Language:zh-cn\r\n");
        sb.append("Accept-Encoding:gzip,deflate\r\n");
        sb.append("User-Agent:Mozilla/4.0(compatible;MSIE 6.0;Window NT 5.0)\r\n");
        sb.append("Connection:Keep-Alive\r\n\r\n");

        //发出HTTP请求
        OutputStream os = socket.getOutputStream();
        os.write(sb.toString().getBytes());
        socket.shutdownOutput();

        //接受响应结果
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len=-1;
        while ((len=is.read(buff))!=-1){
            buffer.write(buff,0,len);
        }

        System.out.println(new String(buffer.toByteArray()));
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        HttpClient client=new HttpClient();
        client.createSocket();
        client.communicate();
    }
}
