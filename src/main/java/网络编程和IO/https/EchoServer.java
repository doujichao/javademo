package 网络编程和IO.https;

import 网络编程和IO.util.EchoUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

public class EchoServer extends EchoUtils {

    private int port=8000;
    private SSLServerSocket serverSocket;

    public EchoServer() throws Exception {
        //输出跟踪日志
        System.setProperty("javax.net.debug","all");
        SSLContext context=createSSLContext();
        SSLServerSocketFactory factory = context.getServerSocketFactory();
        serverSocket= (SSLServerSocket) factory.createServerSocket(port);
        System.out.println("服务器启动");
        System.out.println(serverSocket.getUseClientMode()?"客户模式":"服务器模式");
        System.out.println(serverSocket.getNeedClientAuth()?"需要验证对方身份":"不需要验证对方身份");

        //获取加密套件
        String[] supported = serverSocket.getSupportedCipherSuites();
        serverSocket.setEnabledCipherSuites(supported);

    }

    private SSLContext createSSLContext() throws Exception {
        String keyStoreFile="test.key";//服务器用于证实自己身份的安全证书
        String passphrase="654321";
        //获取key存储实例
        KeyStore ks = KeyStore.getInstance("JKS");
        //获取密码字节数组
        char[] password = passphrase.toCharArray();
        //ks加载密钥文件
        ks.load(new FileInputStream(keyStoreFile),password);
        //获取key管理工厂
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        //工厂根据password初始化key
        kmf.init(ks,password);

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(),null,null);

        //当要求客户端提供安全证书时，服务器可创建TrustManagerFactory
        //并有它创建TrustManager，TrustManager根据与之关联的KeyStore中的信息
        //来决定是否相信客户提供的安全证书

//        String trustStoreFile="client.key";//客户端用于证实自己身份的安全证书
//        KeyStore ts = KeyStore.getInstance("JKS");
//        ts.load(new FileInputStream(trustStoreFile),password);
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//        tmf.init(ts);
//        sslContext.init(kmf.getKeyManagers(),tmf.getTrustManagers(),null);

        return sslContext;
    }

    public String echo(String msg){
        return "echo:"+msg;
    }


    public void service(){
        while (true){
            Socket socket=null;
            try {
                //等待客户端连接
                socket=serverSocket.accept();
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

    public static void main(String[] args) throws Exception {
        new EchoServer().service();
    }
}
