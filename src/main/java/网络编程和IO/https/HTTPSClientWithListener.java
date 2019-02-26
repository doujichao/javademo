package 网络编程和IO.https;

import java.io.IOException;

public class HTTPSClientWithListener  extends HTTPSClient{

    @Override
    public void createSocket() throws IOException {
        super.createSocket();
        socket.addHandshakeCompletedListener(event -> {
            System.out.println("握手结束");
            System.out.println("加密套件为："+event.getCipherSuite());
            System.out.println("会话为："+event.getSession());
            System.out.println("通信对方为："+event.getSession().getPeerHost());
        });
    }
    public static void main(String[] args) throws IOException {
        HTTPSClientWithListener client=new HTTPSClientWithListener();
        client.createSocket();
        client.communicate();
    }
}
