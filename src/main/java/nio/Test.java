package nio;


import java.nio.ByteBuffer;

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
    }
}
