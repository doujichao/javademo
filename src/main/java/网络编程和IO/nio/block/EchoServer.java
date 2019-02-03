package 网络编程和IO.nio.block;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private int port=8000;
    private ServerSocketChannel serverSocketChannel=null;
    /**线程池*/
    private ExecutorService executorService;
    /**线程池中工作线程的数目*/
    private static final int POOL_MULTIPLE=4;

    public EchoServer() throws IOException {
        //创建一个线程池
        executorService=Executors.newFixedThreadPool(
                POOL_MULTIPLE*Runtime.getRuntime().availableProcessors());
        //创建一个ServerSocketChannel对象
        serverSocketChannel=ServerSocketChannel.open();
        //使得在同一个主机上关闭了服务器程序，紧接着在启动该服务器程序时，可以顺利绑定相同的端口
        serverSocketChannel.socket().setReuseAddress(true);
        //把服务器进程与本地一个端口绑定
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动");
    }

    public void service(){
        while (true){
            SocketChannel socketChannel=null;
            try {
                socketChannel=serverSocketChannel.accept();
                executorService.execute(new Handler(socketChannel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
}

class Handler implements Runnable{

    private SocketChannel socketChannel;
    public Handler(SocketChannel socketChannel){
        this.socketChannel=socketChannel;
    }

    @Override
    public void run() {
        handle(socketChannel);
    }

    private void handle(SocketChannel socketChannel) {
        try {
            Socket socket = socketChannel.socket();
            System.out.println("接收到客户连接，来自："+socket.getInetAddress()
            +":"+socket.getPort());
            BufferedReader br=getReader(socket);
            PrintWriter pw=getWriter(socket);

            String msg=null;
            while ((msg=br.readLine())!= null){
                System.out.println(msg);
                pw.println(echo(msg));
                if ("bye".equals(msg)){
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (socketChannel!=null) socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String echo(String msg) {
        return "echo:"+msg;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}