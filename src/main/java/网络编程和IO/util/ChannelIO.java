package 网络编程和IO.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ChannelIO {

    protected SocketChannel socketChannel;
    protected ByteBuffer requestBuffer;
    private static int requestBufferSize=4096;

    public ChannelIO(SocketChannel socketChannel,boolean block) throws IOException {
        this.socketChannel=socketChannel;
        socketChannel.configureBlocking(block);
        requestBuffer=ByteBuffer.allocate(requestBufferSize);
    }

    public SocketChannel getSocketChannel(){
        return socketChannel;
    }

    /**
     * 如果原缓冲区的剩余容量不够，就创建一个新的缓冲区，容量是原来的
     * 两倍，把原来缓冲区的数据复制到新的缓冲区
     */
    protected void resizeRequestBuffer(int remaining){
        if (requestBuffer.remaining() < remaining){
            //把容量增大到原来的两倍
            ByteBuffer bb=ByteBuffer.allocate(requestBuffer.capacity()*2);
            requestBuffer.flip();
            //将原来的缓冲区里面的东西复制到新的缓冲区
            bb.put(requestBuffer);
            requestBuffer=bb;
        }
    }

    /**
     * 接收数据，把它们存放到requestBuffer中，如果requestBuffer
     * 剩余容量不足5%，就通过resizeRequestBuffer()方法扩容
     * @return 读取的字节数，可能为0，也可能为-1，当读到文件末尾时为-1
     * @throws IOException
     */
    public int read() throws IOException{
        resizeRequestBuffer(requestBufferSize/20);
        return socketChannel.read(requestBuffer);
    }

    /**
     * 返回requestBuffer,它存放了请求数据
     * @return
     */
    public ByteBuffer getReadBuffer() {
        return requestBuffer;
    }

    /**
     * 发送指定的ByteBuffer中的数据
     * @return
     * @throws IOException
     */
    public int write(ByteBuffer src) throws IOException{
        return socketChannel.write(src);
    }

    /**
     * 把FileChannel的数据写到SocketChannel中
     * @param fc 文件通道
     * @param pos 当前位置
     * @param len 传输长度
     * @return 传送的长度
     * @throws IOException
     */
    public long transferTo(FileChannel fc,long pos,long len) throws IOException {
        return fc.transferTo(pos,len,socketChannel);
    }

    /**
     * 关闭SocketChannel
     * @throws IOException
     */
    public void close() throws IOException {
        socketChannel.close();
    }
}
