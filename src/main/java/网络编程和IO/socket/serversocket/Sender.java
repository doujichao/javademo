package 网络编程和IO.socket.serversocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
    private String host="localhost";
    private int port=8000;
    private Socket socket;
    /**结束通讯方式*/
    private static int stopWay=1;
    /**自然结束*/
    private final int NATURAL_STOP=1;
    /**突然终止程序*/
    private final int SUDDEN_STOP=2;
    /**关闭socket再结束程序*/
    private final int SOCKET_STOP=3;
    /**关闭输出流，再结束程序*/
    private final int OUTPUT_STOP=4;

    public Sender() throws IOException{
        socket=new Socket(host,port);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length>0){
            stopWay=Integer.parseInt(args[0]);
        }
        new Sender().send();
    }

    private void send() throws InterruptedException, IOException {
        PrintWriter pw=getWriter(socket);
        for (int i=0;i<20;i++){
            String msg="hello_"+i;
            pw.println(msg);
            System.out.println("send:"+msg);
            Thread.sleep(500);
            if(i==2){
                if (stopWay==SUDDEN_STOP){
                    System.out.println("程序突然终止");
                    System.exit(0);
                }else if(stopWay==SOCKET_STOP){
                    System.out.println("关闭socket并终止程序");
                    socket.close();
                    break;
                }else if(stopWay==OUTPUT_STOP){
                    socket.shutdownOutput();
                    System.out.println("关闭输出流并终止程序");
                    break;
                }
            }
        }
        if (stopWay==NATURAL_STOP){
            socket.close();
        }
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
}
