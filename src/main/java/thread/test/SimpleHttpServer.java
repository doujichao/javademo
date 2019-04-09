package thread.test;

import thread.threadpool.DefaultThreadPool;
import thread.threadpool.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    //处理HttpRequest的线程池，默认创建一个工作线程
    static ThreadPool<HttpRequestHandler> threadPool=new DefaultThreadPool<>(1);

    /**
     * SimpleHttpServer的根路径
     */
    static String basePath;

    static ServerSocket serverSocket;
    //服务器监听端口
    static int port=8080;
    public static void setPort(int port){
        if (port > 0){
            SimpleHttpServer.port=port;
        }
    }

    /**
     * 设置SimpleHttpServer的基础路径
     * @param basePath
     */
    public static void setBasePath(String basePath){
        if (basePath !=null && new File(basePath).exists() && new File(basePath).isDirectory()){
            SimpleHttpServer.basePath=basePath;
        }
    }

    /**
     * 启动SimpleHttpServer
     */
    public static void start() throws IOException {
        serverSocket=new ServerSocket(port);
        Socket socket=null;
        while ((socket=serverSocket.accept())!=null){
            //接受一个客户端Socket，生成一个HttpRequestHandler，放入线程池中执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }
    static class HttpRequestHandler implements Runnable{

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line=null;
            BufferedReader br=null;
            BufferedReader reader=null;
            PrintWriter out=null;
            InputStream in=null;
            try {
                reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String head = reader.readLine();

                //有相对路径计算出绝对路径
                String filePath=basePath + head.split(" ")[1];
                out=new PrintWriter(socket.getOutputStream());
                //如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")){
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    int i=0;
                    while ((i=in.read())!=-1){
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server:Molly");
                    out.println("Content-Type:image/jpeg");
                    out.println("Content-Length: "+array.length);
                    out.println("");
                    socket.getOutputStream().write(array,0,array.length);
                }else {
                    br=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out=new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server:Molly");
                    out.println("Content-Type:text/html;charset=UTF-8");
                    out.println("");
                    while ((line=br.readLine())!=null){
                        out.println(line);
                    }
                }
                out.flush();
            } catch (IOException e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            }finally {
                close(br,in,reader,out,socket);
            }
        }

        //关闭流或者Socket
        private static void close(Closeable... closeables){
            if (closeables != null){
                for (Closeable closeable:closeables){
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
