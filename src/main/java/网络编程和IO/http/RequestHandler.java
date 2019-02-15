package 网络编程和IO.http;

import 网络编程和IO.http.core.MalformedRequestException;
import 网络编程和IO.http.core.Request;
import 网络编程和IO.http.core.Response;
import 网络编程和IO.util.ChannelIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class RequestHandler implements Handler{

    private ChannelIO channelIO;
    /**
     *存放http请求的缓冲区
     */
    private ByteBuffer requestBuffer=null;
    /**
     *表示是否已经收到了所有的数据
     */
    private boolean requestReceived=false;
    //表示http请求对象
    private Request request=null;
    /**
     *表示http响应对象
     */
    private Response response=null;

    public RequestHandler(ChannelIO cio) {
        this.channelIO=cio;
    }

    /**
     * 接收http请求，如果已经接收到了http请求的所有数据，就返回true，否则就返回false
     * @return
     */
    private boolean receive(SelectionKey sk)throws IOException{
        ByteBuffer tmp=null;
        //如果已经接收到http请求的所有数据，就返回true
        if (requestReceived)return true;

        //如果已经读到通道的末尾，或者已经读到http请求数据的末尾表示，就返回true
        if (channelIO.read()<0||Request.isComplete(channelIO.getReadBuffer())){
            requestBuffer=channelIO.getReadBuffer();
            return requestReceived=true;
        }
        return false;
    }

    /**
     * 通过Request类的parse方法解析requestByteBuffer中的Http请求数据
     * 构造对应的Request对象
     * @return
     * @throws IOException
     */
    private boolean parse() throws IOException{

        try {
            request=Request.parse(requestBuffer);
            return true;
        } catch (MalformedRequestException e) {
            //如果http请求的格式不正确，就发送错误信息
            response=new Response(Response.Code.BAD_REQUEST,new StringContent(e));
        }

        return false;

    }

    /**
     * 接收http请求，发送http响应
     * @param key
     * @throws IOException
     */
    @Override
    public void handle(SelectionKey key) throws IOException {
        try {
            if (request==null){
                //接收http请求
                if (!receive(key))return;
                requestBuffer.flip();

                //如果成功解析了http请求，就创建一个Response对象
                if(parse()) build();

                try {
                    //准备http响应头
                    response.prepare();
                }catch (IOException e){
                    response.release();
                    response=new Response(Response.Code.NOT_FOUND,
                            new StringContent(e));
                    response.prepare();
                }

                if (send()){
                    //如果http响应没有发送完毕，则需要注册写就绪事件
                    //以便在写就绪事件发生时继续发送数据
                    key.interestOps(SelectionKey.OP_WRITE);
                }else {
                    //如果http响应发送完毕，就断开底层的连接
                    //并且释放Response占用的资源
                    channelIO.close();
                    response.release();
                }
            }else {
                //如果已经接收到Http请求的所有数据
                if (!send()){
                    channelIO.close();
                    response.release();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            channelIO.close();
            if (response!=null){
                response.release();
            }
        }
    }

    /**
     * 发送http响应，如果发送完毕，就返回false，否则就返回true
     * @return
     * @throws IOException
     */
    private boolean send() throws IOException {
        return response.send(channelIO);
    }

    /**
     * 创建http响应
     */
    private void build() throws IOException{
        Request.Action action=request.action();
        //仅支持GET和HEAD请求方式
        if ((action!=Request.Action.GET)&&(action!=
        Request.Action.HEAD)){
            response=new Response(Response.Code.METHOD_NOT_ALLOWED
                    ,new StringContent("Method Not Allowed"));
        }else {
            response=new Response(Response.Code.OK,
                    new FileContent(request.uri()),action);
        }
    }
}
