package 网络编程和IO.https;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;

public class EchoServer {

    private int port=8000;
    private SSLServerSocket serverSocket;

    public EchoServer() throws IOException {
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

    private SSLContext createSSLContext() {
        return null;
    }

}
