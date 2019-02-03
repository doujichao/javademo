package 网络编程和IO.nio.block;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class EchoClient {

    private SocketChannel socketChannel=null;

    public EchoClient() throws IOException{
        socketChannel=SocketChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa=new InetSocketAddress(ia,8000);
        socketChannel.connect(isa);
        System.out.println("与服务器连接建立成功");
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().talk();
    }

    private void talk() {
        try {
            BufferedReader br = getReader(socketChannel.socket());
            PrintWriter pw = getWriter(socketChannel.socket());
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while ((msg=localReader.readLine())!=null){
                pw.println(msg);
                System.out.println(br.readLine());
                if (msg.equals("bye"))break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(),true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
