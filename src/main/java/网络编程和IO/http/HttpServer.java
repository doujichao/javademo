package 网络编程和IO.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class HttpServer {

    private Selector selector;
    private int port=80;
    private ServerSocketChannel serverSocketChannel;
    private Charset charset=Charset.forName("utf-8");

    public HttpServer () throws IOException{
        //开启挑选器
        selector=Selector.open();
        //开启serverSocket
        serverSocketChannel=ServerSocketChannel.open();
        //设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //设置可重复启动
        serverSocketChannel.socket().setReuseAddress(true);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server=new HttpServer();
        server.service();
    }

    public void service()  throws IOException{

        //注册连接就绪事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,new AcceptHandler());

        for (;;){
            int n = selector.select();
            if (n==0){
                continue;
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            SelectionKey key=null;
            while (it.hasNext()){
                try {
                    key = it.next();
                    it.remove();
                    final Handler handler= (Handler) key.attachment();
                    handler.handle(key);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (key!=null){
                        key.cancel();
                        key.channel().close();
                    }
                }

            }

        }

    }

}


