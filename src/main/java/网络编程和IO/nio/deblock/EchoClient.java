package 网络编程和IO.nio.deblock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoClient {

    private SocketChannel socketChannel=null;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer=ByteBuffer.allocate(1024);
    private Charset charset=Charset.forName("utf-8");

    private Selector selector;

    public EchoClient() throws IOException{
        socketChannel=SocketChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa=new InetSocketAddress(ia,8000);
        socketChannel.connect(isa);//采用阻塞模式连接服务器
        socketChannel.configureBlocking(false);//设置为非阻塞模式
        System.out.println("与服务器连接建立成功");
        selector=Selector.open();
    }

    public static void main(String[] args) throws IOException {
       final EchoClient client = new EchoClient();
        new Thread(() -> client.receiveFromUser()).start();
        client.talk();
    }

    /**
     * 接收用户从控制台输入的数据，把它放到sendBuffer中
     */
    public void receiveFromUser()  {
        try {
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while ((msg=localReader.readLine())!=null){
                synchronized (sendBuffer){
                    sendBuffer.put(encode(msg+"\r\n"));
                }

                if ("bye".equals(msg)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void talk() {
        try {
            socketChannel.register(selector, SelectionKey.OP_READ|
                    SelectionKey.OP_WRITE);
            while (selector.select()>0){
                Set<SelectionKey> readyKeys =
                        selector.selectedKeys();
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
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(SelectionKey key) throws IOException {
        //发送sendBuffer中的数据
        SocketChannel socketChannel= (SocketChannel) key.channel();
        synchronized (sendBuffer){
            //limit=position,position=0
            sendBuffer.flip();
            socketChannel.write(sendBuffer);
            //删除已经发送的数据
            sendBuffer.compact();
        }
    }

    private void receive(SelectionKey key) throws IOException {
        //接收EchoServer发送的数据，把它放到receiveBuffer中
        //如果receiveBuffer中有一行数据，就打印数据，然后把它从receiveBuffer中删除
        SocketChannel socketChannel= (SocketChannel) key.channel();
        socketChannel.read(receiveBuffer);
        receiveBuffer.flip();
        String receiveData=decode(receiveBuffer);

        if (receiveData.indexOf("\r\n")==-1){
            return;
        }

        String outputData = receiveData.substring(0, receiveData.indexOf("\r\n") + 1);
        System.out.println(outputData);
        if ("echo:byte\r\n".equals(outputData)){
            key.cancel();
            key.channel().close();
            System.out.println("关闭与服务器的连接");
            selector.close();
            System.exit(0);
        }

        ByteBuffer temp = encode(outputData);
        receiveBuffer.position(temp.limit());
        receiveBuffer.compact();
    }

    private String decode(ByteBuffer receiveBuffer) {
        return charset.decode(receiveBuffer).toString();
    }

    private ByteBuffer encode(String s) {
        return charset.encode(s);
    }

}
