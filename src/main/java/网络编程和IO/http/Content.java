package 网络编程和IO.http;

public interface Content extends Sendable{
    /**
     * 返回正文的类型
     * @return
     */
    String type();

    /**
     * 返回正文的长度，在正文还没有准备之前，即还没有调用prepare方法之前，
     * length方法返回-1
     * @return
     */
    long length();
}
