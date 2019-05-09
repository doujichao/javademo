package 网络编程和IO.nio;

import org.mortbay.util.IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel，监听8080端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        //设置非阻塞模式
        ssc.configureBlocking(false);
        //为ssc注册选择器
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        Handler handler=new Handler(1023);
        while (true){
            //等待请求，每次等待阻塞3s，超过3s后线程继续向下运行，如果传入0或者不传参数将一直阻塞
            if (selector.select(3000) == 0){
                System.out.println("等待请求超时....");
                continue;
            }
            System.out.println("处理请求...");
            //获取待处理的SelectionKey
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                try {
                    //接收到的连接请求
                    if (key.isAcceptable()){
                        handler.handleAccept(key);
                    }
                    //读数据
                    if (key.isReadable()){
                        handler.handleRead(key);
                    }
                }catch (IOException ex){
                    keyIterator.remove();
                    continue;
                }
                //处理完成之后，需要将该key从迭代器中移除
                keyIterator.remove();
            }
        }
    }

    private static class Handler{
        private int bufferSize=1024;
        private String localCharset="UTF-8";
        public Handler(){}
        public Handler(int bufferSize){
            this(bufferSize,null);
        }

        public Handler(String localCharset){
            this(-1,localCharset);
        }

        public Handler(int bufferSize, String localCharset) {
            if (bufferSize>0){
                this.bufferSize=bufferSize;
            }
            if (localCharset !=null){
                this.localCharset=localCharset;
            }
        }

        public void handleAccept(SelectionKey key) throws IOException {
            //接受请求，此时挑选的是OP_ACCEPT状态的请求
            SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
            //配置请求非阻塞模式
            sc.configureBlocking(false);
            //注册可读
            sc.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey key) throws IOException {
            //获取channel
            SocketChannel sc= (SocketChannel) key.channel();
            //获取buffer并重置
            ByteBuffer buffer= (ByteBuffer) key.attachment();
            //这时候还没有开始读取通道流
            buffer.clear();
            if (sc.read(buffer)==-1){
                sc.close();
            }else {
                //将buffer转换为就读状态
                buffer.flip();
                String receiveString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("received from client:"+receiveString);
                //返回数据给客户端
                String sendString="received data:"+receiveString;
                buffer = ByteBuffer.wrap(sendString.getBytes());
                sc.write(buffer);
                sc.close();
            }

        }

    }

}
