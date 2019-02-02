package 网络编程和IO.socket.echo;


import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class EchoServer {

    private int port=8000;
    private ServerSocket serverSocket;
    //线程池
    private ExecutorService executorService;
    //单个CPU时线程池中工作线程的数量
    private final int POOL_SIZE=4;

    //用于监听关闭服务器命令的端口
    private int portForShutDown=8001;
    private ServerSocket serverSocketForShutdown;
    //服务器是否关闭
    private boolean isShutDown=false;

    private Thread shutdownThread=new Thread(){
        public void start(){
            this.setDaemon(true);
            super.start();
        }

        @Override
        public void run() {
            while (!isShutDown){
                Socket socketForShutDown=null;
                try {
                    socketForShutDown=serverSocketForShutdown.accept();
                    BufferedReader br=new BufferedReader(
                            new InputStreamReader(socketForShutDown.getInputStream()));
                    String command = br.readLine();
                    if ("shutdown".equals(command)){
                        long beginTime = System.currentTimeMillis();
                        socketForShutDown.getOutputStream().write("服务器正在关闭\r\n".getBytes());
                        isShutDown=true;

                        //请求关闭线程池
                        //线程池不再接收新的任务，但是会继续执行完工作队列中现有的任务
                        executorService.shutdown();

                        //等待关闭线程池，每次等待的时间超过30秒
                        while (!executorService.isTerminated()){
                            executorService.awaitTermination(30, TimeUnit.SECONDS);
                        }

                        //关闭与EchoClient客户通讯的ServerSocket
                        serverSocket.close();
                        long endTime = System.currentTimeMillis();
                        socketForShutDown.getOutputStream().write(("服务器已经关闭，服务器" +
                                "关闭用了"+(endTime-beginTime)+"毫秒\r\n").getBytes());
                        socketForShutDown.close();
                        serverSocketForShutdown.close();

                    }else {
                        socketForShutDown.getOutputStream().write("错误的命令\r\n".getBytes());
                        socketForShutDown.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public EchoServer() throws IOException{
        serverSocket=new ServerSocket(port);
        //设置等待客户端连接时间未60秒
        serverSocket.setSoTimeout(60000);
        serverSocketForShutdown=new ServerSocket(portForShutDown);

        //创建线程池
        executorService= Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        shutdownThread.start();
        System.out.println("服务启动");
    }

    public void service(){
        while (!isShutDown){
            Socket socket=null;
            try {
                socket= serverSocket.accept();
                //可能会抛出SocketTimeoutException和SocketException
                socket.setSoTimeout(60000);
                executorService.execute(new Handler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
}


class Handler implements Runnable{

    private Socket socket;

    public Handler(Socket socket){
        this.socket=socket;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public StringBuffer echo(String msg){
        return new StringBuffer("echo:"+msg);
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection accepted"+socket.getInetAddress()+":"+socket.getPort());
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String msg=null;
            while ((msg=br.readLine())!=null){
                System.out.println(msg);
                pw.println(echo(msg));
                if (msg.equals("bye")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}