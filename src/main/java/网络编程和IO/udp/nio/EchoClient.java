package 网络编程和IO.udp.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;

public class EchoClient {
    private DatagramChannel datagramChannel=null;
    private ByteBuffer sendBuffer=ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer=ByteBuffer.allocate(1024);
    private Charset charset=Charset.forName("utf-8");
    private Selector selector;

    public EchoClient() throws IOException {
        this(7000);
    }

    public EchoClient(int port) throws IOException {
        datagramChannel=DatagramChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa=new InetSocketAddress(ia,port);
        //设置非阻塞模式
        datagramChannel.configureBlocking(false);
        //与本地地址绑定
        datagramChannel.socket().bind(isa);
        isa=new InetSocketAddress(ia,8000);
        //与远程地址连接
        datagramChannel.connect(isa);
        selector=Selector.open();
    }

    public static void main(String[] args) throws IOException {
        int port=7000;
        if (args.length>0){
            port=Integer.parseInt(args[0]);
        }
        final EchoClient client=new EchoClient();
        Thread thread= new Thread(() -> client.receiveFromUser());
        thread.start();
        client.talk();
    }

    private void talk() throws IOException {
        datagramChannel.register(selector,
                SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        while (selector.select()>0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
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

                }catch (IOException e){

                }
            }
        }
    }

    public void receive(SelectionKey key) throws IOException {
        DatagramChannel datagramChannel= (DatagramChannel) key.channel();
        datagramChannel.read(receiveBuffer);
        receiveBuffer.flip();
        String receiveData = decode(receiveBuffer);
        if (receiveData.indexOf("\r\n")==-1){
            return;
        }
        String outputData = receiveData.substring(0, receiveData.indexOf("\r\n") + 1);
        System.out.println(outputData);
        if (outputData.equals("echo:bye\r\n")){
            key.cancel();
            datagramChannel.close();
            System.out.println("关闭与服务器的连接");
            selector.close();
            System.exit(-1);
        }
        ByteBuffer encode = encode(outputData);
        receiveBuffer.position(encode.limit());
        receiveBuffer.compact();
    }

    public void send(SelectionKey key) throws IOException {
        DatagramChannel datagramChannel= (DatagramChannel) key.channel();
        synchronized (sendBuffer){
            //limit=position,position=0
            sendBuffer.flip();
            datagramChannel.write(sendBuffer);
            //将缓冲区中剩下的内容重新
            sendBuffer.compact();
        }
    }


    private void receiveFromUser() {
        try {
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while ((msg=localReader.readLine())!=null){
                //多个线程可能回对sendBuffer进行操作，这里进行了同步操作
                synchronized (sendBuffer){
                    sendBuffer.put(encode(msg+"\r\n"));
                }

                if (msg.equals("bye")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String decode(ByteBuffer buffer){
        return charset.decode(buffer).toString();
    }

    public ByteBuffer encode(String msg){
        return charset.encode(msg);
    }
}
