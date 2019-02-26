package 网络编程和IO.https;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPSClient {
    String host="www.usps.com";
    int port=443;
    SSLSocketFactory factory;
    SSLSocket socket;

    public void createSocket() throws IOException {
        factory= (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket= (SSLSocket) factory.createSocket(host,port);
        String[] supported = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(supported);
    }

    public void communicate() throws IOException {
        StringBuffer sb=new StringBuffer("GET http://"+host+"/HTTP/1.1/\r\n");
        sb.append("Host:"+host+"\r\n");
        sb.append("Accept:*/*\r\n");
        sb.append("\r\n");

        //发出http请求
        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(sb.toString().getBytes());

        //接收响应结果
        InputStream socketIn = socket.getInputStream();
        ByteArrayOutputStream buff=new ByteArrayOutputStream();
        byte[] buffer =new byte[1024];
        int len=-1;
        while ((len=socketIn.read(buffer))!=-1){
            buff.write(buffer,0,len);
        }
        //把字节数组转换为字符串，并且只显示部分内容
        System.out.println(new String(buff.toByteArray()).substring(1,1000));
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        HTTPSClient client=new HTTPSClient();
        client.createSocket();
        client.communicate();
    }
}
