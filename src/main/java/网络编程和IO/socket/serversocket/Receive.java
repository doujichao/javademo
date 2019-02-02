package 网络编程和IO.socket.serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive {

    private int port=8000;
    private ServerSocket serverSocket;
    /**结束通讯方式*/
    private static int stopWay=1;
    /**自然结束*/
    private final int NATURAL_STOP=1;
    /**突然终止程序*/
    private final int SUDDEN_STOP=2;
    /**关闭socket再结束程序*/
    private final int SOCKET_STOP=3;
    /**关闭输出流，再结束程序*/
    private final int INPUT_STOP=4;
    /**关闭ServerSocket，再结束程序*/
    private final int SERVERSOCKET_STOP=5;

    public Receive() throws IOException{
        serverSocket=new ServerSocket(port);
        System.out.println("服务器已经启动");
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void receive() throws InterruptedException, IOException {
        Socket socket=null;
        socket=serverSocket.accept();
        BufferedReader br = getReader(socket);
        for (int i=0;i<20;i++){
            String msg = br.readLine();
            System.out.println("receive:"+msg);
            Thread.sleep(1000);
            if(i==2){
                if (stopWay==SUDDEN_STOP){
                    System.out.println("程序突然终止");
                    System.exit(0);
                }else if(stopWay==SOCKET_STOP){
                    System.out.println("关闭socket并终止程序");
                    socket.close();
                    break;
                }else if(stopWay==INPUT_STOP){
                    System.out.println("关闭输出流并终止程序");
                    socket.shutdownInput();
                    break;
                }else if (stopWay==SERVERSOCKET_STOP){
                    System.out.println("关闭ServerSocket并终止程序");
                    serverSocket.close();
                    break;
                }
            }
        }
        if (stopWay==NATURAL_STOP){
            socket.close();
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length>0){
            stopWay=Integer.parseInt(args[0]);
        }
        new Receive().receive();
    }
}
