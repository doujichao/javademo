package 网络编程和IO.nio;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * 测试缓冲区
 * ServerSocketChannel:ServerSocket的替代类，支持阻塞通讯和非阻塞通讯
 * SocketChannel:Socket的替代类，支持阻塞通讯和非阻塞通讯
 * Selector:未ServerSocketChannel监控接收连接就绪事件，未SocketChannel监控连接就绪，度就绪，和写就绪事件
 * SelectionKey:代表ServerSocketChannel以SocketChannel向Selector注册事件的句柄
 *          当一个SelectionKey对象位于Selector对象的selected-keys集合中时，就表示这个
 *          SelectionKey对象相关的事件发生了
 *
 */
public class Test {
    @org.junit.Test
    public void testBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(100);
        //0
        System.out.println(buffer.position());
        //100
        System.out.println(buffer.limit());
        //100
        System.out.println(buffer.capacity());
        //100
        System.out.println(buffer.remaining());

        //写入数据
        buffer.put((byte)1);
        buffer.put((byte)2);
        buffer.put((byte)3);
        buffer.put((byte)4);

        //4
        System.out.println(buffer.position());
        //100
        System.out.println(buffer.limit());
        //100
        System.out.println(buffer.capacity());
        //96remaining=limit-position
        System.out.println(buffer.remaining());

//        buffer.position(1);

        /**
         * 拍板：position=0，limit=position
         */
        buffer.flip();
        byte b = buffer.get();
        System.out.println(b);

        buffer.slice();
        /*
         * 删除从0到position的内容，将position到limit
         * 的内容复制到从0开始的位置，
         * 同时将position=limit-position,limit=capacity
         */
        buffer.compact();
        /*
         *重新设置limit=capacity,position=0
         */
        buffer.clear();
        /*
         *重新读取：position=0
         */
        buffer.rewind();
    }

    /**
     * 测试通道拷贝
     * @throws Exception
     */
    @org.junit.Test
    public void testChannelCopy() throws Exception {
        //随机访问文件
        RandomAccessFile srcRaf=new RandomAccessFile("F:\\c.zip","r");
        //源文件通道
        FileChannel srcChannel = srcRaf.getChannel();

        //缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

        //随机访问文件
        RandomAccessFile destRaf=new RandomAccessFile("F:\\d.zip","rw");
        //源文件通道
        FileChannel destChannel = destRaf.getChannel();

        while ((srcChannel.read(buffer))!=-1){
            //需要进行拍板
            buffer.flip();
            destChannel.write(buffer);
            //清除,重新将position置于缓冲区头
            buffer.clear();
        }
        srcChannel.close();
        srcRaf.close();
        destChannel.close();
        destRaf.close();
    }

    /**
     * 测试内存缓冲映射
     * @throws Exception
     */
    @org.junit.Test
    public void testMemoryMapper() throws Exception {
        //创建随机访问文件
        RandomAccessFile raf=new RandomAccessFile("F:\\c.zip","rw");
        //得到文件通道
        FileChannel channel = raf.getChannel();

        System.out.println("fileLength :"+raf.length());

        //创建与map对应的内存字节缓冲区
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());

        System.out.println("mapLength:"+map.capacity());

    }

    /**
     * Charset:提供了编码和解码的方法
     * encode(String str)
     * encode(CharBuffer cb)
     * decode(ByteBuffer bb)
     */
    @org.junit.Test
    public void testCharset(){
        Charset charset=Charset.forName("GBK");
        ByteBuffer encode = charset.encode("你好");
        System.out.println(encode);
    }

    /**
     * SelectableChannel的主要方法如下：
     * configureBlocking(boolean block):设置阻塞和非阻塞模式
     *
     * 注册Selector事件
     * register(Selector sel,int ops):
     * register(Selector sel,int ops,Object attachment):
     */
    @org.junit.Test
    public void testSelectableChannel(){

    }
}
