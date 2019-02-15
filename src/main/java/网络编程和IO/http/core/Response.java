package 网络编程和IO.http.core;

import 网络编程和IO.http.Content;
import 网络编程和IO.http.Sendable;
import 网络编程和IO.util.ChannelIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Response implements Sendable {

    /**
     * 返回码的枚举类
     */
    public static class Code{
        private int number;
        private String reason;
        private Code(int i,String r){number=i;reason=r;}
        public static Code OK=new Code(200,"OK");
        public static Code BAD_REQUEST=new Code(400,"Bad Request");
        public static Code NOT_FOUND=new Code(404,"Not Found");
        public static Code METHOD_NOT_ALLOWED=new Code(405,"Method Not Allowed");
    }

    /**
     * 状态代码
     */
    private Code code;

    /**
     * 响应正文
     */
    private Content content;

    /**
     * 表示http响应中是否只包含响应头
     */
    private boolean headersOnly;

    /**
     * 响应头
     */
    private ByteBuffer headerBuffer=null;

    public Response(Code rc,Content c){
        this(rc,c,null);
    }

    public Response(Code rc, Content c, Request.Action o) {
        code=rc;
        content=c;
        headersOnly=(o==Request.Action.HEAD);
    }

    private static String CRLF="\r\n";
    private static Charset responseCharset=Charset.forName("utf-8");

    /**
     * 创建响应头的内容，把它存放到一个ByteBuffer中
     * @return
     */
    private ByteBuffer headers(){
        CharBuffer cb = CharBuffer.allocate(1024);
        for (;;){
            try {
                cb.put("HTTP/1.1").put(code.toString()).put(CRLF);
                cb.put("Server:nio/1.1").put(CRLF);
                cb.put("Content-type:").put(content.type()).put(CRLF);
                cb.put("Content-length:").put(Long.toString(content.length())).put(CRLF);
                cb.put(CRLF);
                break;
            } catch (Exception e) {
                assert(cb.capacity()<(1<<16));
                cb=CharBuffer.allocate(cb.capacity()*2);
                continue;
            }
        }

        cb.flip();
        return responseCharset.encode(cb);
    }

    /**
     * 准备http响应中的正文，以及响应头的内容
     * @throws IOException
     */
    public void prepare() throws IOException{
        content.prepare();
        headerBuffer=headers();
    }

    public boolean send(ChannelIO cio) throws IOException{
        if (headerBuffer==null){
            throw  new IllegalStateException();
        }

        //发送响应头
        if (headerBuffer.hasRemaining()){
            if (cio.write(headerBuffer)<=0){
                return true;
            }
        }

        //发送响应正文
        if (!headersOnly){
            if (content.send(cio)){
                return true;
            }
        }

        return false;
    }


    public void release() throws IOException{
        content.release();
    }
}
