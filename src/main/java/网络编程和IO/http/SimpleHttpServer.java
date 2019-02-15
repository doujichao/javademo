package 网络编程和IO.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServer {
    private int port=80;
    private ServerSocketChannel serverSocketChannel;
    private ExecutorService executorService;
    private static final int POOL_MULTIPLE=4;

    public SimpleHttpServer() throws IOException {
        executorService= Executors.newFixedThreadPool(
          Runtime.getRuntime().availableProcessors()*POOL_MULTIPLE
        );
        serverSocketChannel=ServerSocketChannel.open();
        //设置Reuse可以使应用可以直接在原端口重启
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动");
    }

    public void server(){
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
        new SimpleHttpServer().server();
    }

    /**
     * 负责处理http请求的内部类
     */
    class Handler implements Runnable{

        private SocketChannel socketChannel;
        private Charset charset=Charset.forName("utf-8");

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
                System.out.println("接收到客户端连接，来自："+socket.getInetAddress()
                +":"+socket.getPort());
                //创建1k的缓冲区
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                //接收http请求，假定其长度不超过1024个字节
                socketChannel.read(buffer);
                buffer.flip();
                String request = decode(buffer);
                System.out.println(request);

                //生成http响应结果,并发送http响应的第一行和响应头
                StringBuffer sb=new StringBuffer("HTTP/1.1 200 OK \r\n");
                sb.append("Content-Type:text/html\r\n\r\n");
                socketChannel.write(encode(sb.toString()));

                FileInputStream in;
                //获取Http请求的第一行
                String firstLine = request.substring(0, request.indexOf("\r\n"));
                if (firstLine.indexOf("login.html")!=-1){
                    in=new FileInputStream("");
                }else{
                    in=new FileInputStream("");
                }
                FileChannel fileChannl = in.getChannel();
                fileChannl.transferTo(0,fileChannl.size(),socketChannel);

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (socketChannel!=null){
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public String decode(ByteBuffer buffer){
            return charset.decode(buffer).toString();
        }

        public ByteBuffer encode(String str){
            return charset.encode(str);
        }
    }
}
