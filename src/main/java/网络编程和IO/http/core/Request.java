package 网络编程和IO.http.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    /**
     * 枚举类，表示http请求的方式
     */
    public static class Action{
        private String name;
        private Action(String name){this.name=name;}
        public static Action GET=new Action("GET");
        static Action PUT=new Action("PUT");
        static Action POST=new Action("POST");
        public static Action HEAD=new Action("HEAD");

        public static Action parse(String s){
            if ("GET".equals(s)){
                return GET;
            }
            if ("PUT".equals(s)){
                return PUT;
            }
            if ("POST".equals(s)){
                return POST;
            }
            if ("HEAD".equals(s)){
                return HEAD;
            }
        throw new IllegalArgumentException(s);
        }
    }

    private Action action;
    private String version;
    private URI uri;

    public Action action(){
        return action;
    }

    public String version(){
        return version;
    }

    public URI uri(){
        return uri;
    }

    /**
     * 构建request对象
     * @param a 请求类型
     * @param v http版本
     * @param u uri
     */
    private Request(Action a,String v,URI u){
        action=a;
        version=v;
        uri=u;
    }

    @Override
    public String toString() {
        return action +" " + version + " " + uri ;
    }

    private static Charset requestCharset=Charset.forName("utf-8");

    /**
     * 判断ByteBuffer是否包含了HTTP请求的所有数据，http请求以\r\n\r\n结尾
     * @param readBuffer
     * @return
     */
    public static boolean isComplete(ByteBuffer readBuffer) {
        ByteBuffer temp = readBuffer.asReadOnlyBuffer();
        temp.flip();
        String data = requestCharset.decode(temp).toString();
        if (data.indexOf("\r\n\r\n")!=-1){
            return true;
        }
        return false;
    }

    /**
     * 删除请求正文，本范例仅支持GET和HEAD请求方法，不处理HTTP请求中的正文部分
     * @param bb
     * @return
     */
    private static ByteBuffer deleteContent(ByteBuffer bb){
        ByteBuffer temp=bb.asReadOnlyBuffer();
        String data = requestCharset.decode(temp).toString();
        if (data.indexOf("\r\n\r\n")!=-1){
            data=data.substring(0,data.indexOf("\r\n\r\n")+4);
            return requestCharset.encode(data);
        }
        return bb;
    }

    /**
     * 设定用于解析Http请求的字符串匹配模式，对于以下形式的HTTP请求：
     *
     *      GET /dir/file HTTP/1.1
     *      Host:hostname
     * 将被解析成：
     *     group[1]="GET"
     *     group[2]="/dir/file"
     *     group[3]="1.1"
     *     group[4]="hostname"
     *
     */
    private static Pattern requestPattern= Pattern.
            compile("\\A([A-Z]+)+HTTP/([0-9\\.]+)$" +
                    ".*^Host:([^ ]+)$.*\r\n\r\n\\z",Pattern.MULTILINE|Pattern.DOTALL);

    /**
     * 解析http请求
     * @param bb
     * @return
     * @throws MalformedRequestException
     */
    public static Request parse(ByteBuffer bb) throws MalformedRequestException{
        //删除请求正文
        bb=deleteContent(bb);
        //解码
        CharBuffer cb = requestCharset.decode(bb);
        //进行字符串匹配
        Matcher m = requestPattern.matcher(cb);
        //如果http请求与指定的字符串模式不匹配，说明请求数据不正确
        if (!m.matches()){
            throw new MalformedRequestException();
        }
        Action a;
        try {//获取请求方式
            a=Action.parse(m.group(1));
        } catch (IllegalArgumentException e) {
            throw new MalformedRequestException();
        }
        URI u;

        try {
            u=new URI("http://"+m.group(4)+m.group(2));
        } catch (URISyntaxException e) {
            throw new MalformedRequestException();
        }
        //创建一个Request对象，并将 其返回
        return new Request(a,m.group(3),u);
    }
}
