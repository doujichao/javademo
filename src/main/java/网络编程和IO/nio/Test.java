package 网络编程和IO.nio;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试缓冲区
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
        //96
        System.out.println(buffer.remaining());

//        buffer.position(1);

        //拍板
        buffer.flip();
        byte b = buffer.get();
        System.out.println(b);

        buffer.slice();
        buffer.compact();
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
}
