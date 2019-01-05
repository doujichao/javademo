package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务器端套接字通道
 */
public class MyServerSocketChannel {

    private static String str;

    public static void main(String[] args) throws Exception {
        //打开一个挑选器
        Selector selector=Selector.open();
        //开启服务器socket通道，但是为绑定
        ServerSocketChannel ssc=ServerSocketChannel.open();
        //创建绑定地址
        InetSocketAddress address=new InetSocketAddress("localhost",8888);
        //绑定地址
        ssc.bind(address);
        //配置非阻塞模式
        ssc.configureBlocking(false);
        //在挑选器中注册ssc，指定接收事件(感兴趣的事件)
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            //让挑选器开始挑选，该方法时阻塞的，起码最少一个被挑选出来
            selector.select();
            //获得挑选key集合
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iterator = set.iterator();

            //缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            SocketChannel accept;

            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //1、判断ssc是否发生了accept事件
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    //接受连接
                    accept = channel.accept();

                    //配置非阻塞模式
                    accept.configureBlocking(false);

                    //注册socketChannel
                    accept.register(selector,SelectionKey.OP_READ | SelectionKey.OP_CONNECT
                    |SelectionKey.OP_WRITE);

                }

                //2、判断可连接
                if(key.isConnectable()){
                    accept= (SocketChannel) key.channel();
                    System.out.println(accept+":connectable!!!");
                }

                //3、判断可读
                if(key.isReadable()){
                    buffer.clear();
                    accept= (SocketChannel) key.channel();
                    int len=accept.read(buffer);
                    if(len==-1){//通道关闭
                        key.cancel();
                        System.out.println("Client closed!!");
                    }else if(len>0){
                        System.out.println(len);
                        //缓存中有数据
                        buffer.flip();
                        byte[] arr = buffer.array();
                        str = new String(arr,0,buffer.limit());
                        System.out.println("from Client:"+str);
                        buffer.clear();
                    }
                }

                //4、isWritable
                if(key.isWritable()){
                    accept= (SocketChannel) key.channel();
                    buffer.clear();
                    //put方法position位置会移动
                    buffer.put("hi,I'm Server!!".getBytes());
                    buffer.flip();
                    accept.write(buffer);
                    System.out.println("Server : write to "+accept);
                    buffer.clear();
                }

                //删除当前的元素
                iterator.remove();
                Thread.sleep(500);
            }
        }
    }
}
