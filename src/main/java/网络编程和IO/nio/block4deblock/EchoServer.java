package 网络编程和IO.nio.block4deblock;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    private Selector selector=null;
    private int port=8000;
    private ServerSocketChannel serverSocketChannel=null;
    private Charset charset=Charset.forName("utf-8");

    public EchoServer() throws IOException {
        //创建一个挑选器对象
        selector=Selector.open();
        //创建一个ServerSocketChannel对象
        serverSocketChannel=ServerSocketChannel.open();
        //使得在同一个主机上关闭了服务器程序，紧接着在启动该服务器程序时，可以顺利绑定相同的端口
        serverSocketChannel.socket().setReuseAddress(true);
        //把服务器进程与本地一个端口绑定
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动");
    }

    public void accept(){
        for (;;){
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("接收到客户连接，来自："+socketChannel.socket().getInetAddress()+
                        ":"+socketChannel.socket().getPort());
                socketChannel.configureBlocking(false);

                ByteBuffer buffer = ByteBuffer.allocate(1024);


                synchronized (gate){
                    selector.wakeup();
                    socketChannel.register(selector,SelectionKey.OP_READ|
                            SelectionKey.OP_WRITE,buffer);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个锁对象
     */
    private Object gate=new Object();

    public void service() throws Exception {

        for (;;){
            synchronized (gate){
                int n = selector.select();
                if (n==0){
                    continue;
                }
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = readyKeys.iterator();
                while (it.hasNext()){
                    SelectionKey key=null;
                    try {
                        key=it.next();
                        it.remove();
                        if (key.isReadable()){
                            receive(key);
                        }

                        if (key.isWritable()){
                            send(key);
                        }

                    }catch (Exception e){
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

    private void send(SelectionKey key) throws IOException {
        //获得与SelectionKey关联的ByteBuffer
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        //获得与SelectionKey关联的socketChannel
        SocketChannel socketChannel= (SocketChannel) key.channel();
        //limit=position,position=0
        buffer.flip();
        //按照utf-8编码，将buffer中的字节转换为字符串
        String data=decode(buffer);
        //如果还没有读到一行数据，就返回
        if (data.indexOf("\r\n")==-1){
            return;
        }
        //截取一行数据
        String outputData = data.substring(0, data.indexOf("\n") + 1);
        System.out.println(outputData);

        //把输出的字符串按照Utf-8编码，转换为字节，把它放到outputBuffer中
        ByteBuffer outputBuffer = encode("echo:" + outputData);
        //如果outputBuffer还有字符，输出来
        while (outputBuffer.hasRemaining()){
            socketChannel.write(outputBuffer);
        }
        //把outputData，字符串按照utf-8转换为字节，放入ByteBuffer中
        ByteBuffer temp = encode(outputData);
        //将buffer的位置设置为temp的极限
        buffer.position(temp.limit());
        //删除0到position的数据，将数据复制到0到limit-position
        buffer.compact();

        if (outputData.equals("\r\n")){
            key.cancel();
            socketChannel.close();
            System.out.println("关闭与客户的连接");
        }
    }

    private String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    private ByteBuffer encode(String str){
        return charset.encode(str);
    }

    private void receive(SelectionKey key) throws IOException {
        //获得与SelectionKey关联的附件
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        //获得SelectionKey关联的SocketChannel
        SocketChannel socketChannel= (SocketChannel) key.channel();
        //创建一个byteBuffer，用于存放读到的数据
        ByteBuffer readBuf = ByteBuffer.allocate(32);
        //读取数据
        socketChannel.read(readBuf);
        readBuf.flip();

        //将buffer的极限设置为容量，扩大一下存储的容量
        buffer.limit(buffer.capacity());
        //把readBuf中的内容拷贝到buffer中
        buffer.put(readBuf);
    }

    public static void main(String[] args) throws Exception {
        final EchoServer server = new EchoServer();
        Thread accept= new Thread(() -> server.accept());
        accept.start();
        server.service();
    }
}
