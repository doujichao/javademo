package 网络编程和IO.https;

import 网络编程和IO.util.EchoUtils;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class EchoClient extends EchoUtils {

    private String host="localhost";
    private int port=8000;
    private SSLSocket socket;

    public EchoClient() throws IOException {
        SSLSocketFactory factory= (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket=(SSLSocket)factory.createSocket(host,port);
        String[] supported = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(supported);
        System.out.println(socket.getUseClientMode() ? "客户模式":"服务器模式");
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().talk();
    }

    public void talk() throws IOException{
        try {
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while ((msg=localReader.readLine())!=null){
                pw.println(msg);
                System.out.println(br.readLine());
                if (msg.equals("bye")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
