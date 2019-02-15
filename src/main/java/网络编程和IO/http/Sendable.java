package 网络编程和IO.http;

import 网络编程和IO.util.ChannelIO;

import java.io.IOException;

public interface Sendable {
    /**
     * 准备需要发送的内容
     * @throws IOException
     */
    void prepare() throws IOException;

    /**
     * 利用通道发送部分内容，如果所有内容发送完毕，就返回false
     * 如果还有内容未发送，就返回true，如果内容还没准备好，就抛出IllegalStateException
     * @param cio 需要进行操作的通道
     * @return 是否操作成功
     * @throws IOException
     */
    boolean send(ChannelIO cio) throws IOException;

    /**
     * 当服务器发送完毕，就调用此方法，释放内容占用的资源
     * @throws IOException
     */
    void release() throws IOException;
}
