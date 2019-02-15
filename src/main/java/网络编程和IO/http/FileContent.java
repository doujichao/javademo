package 网络编程和IO.http;

import 网络编程和IO.util.ChannelIO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;

public class FileContent implements Content {

    /**
     * 假定文件的根目录未root，该目录位于classpath下
     */
    private static File ROOT=new File("root");
    /**
     * 文件对象
     */
    private  File file;

    public FileContent(URI uri) {
        file=new File(ROOT,uri.getPath().replace('/',File.separatorChar));
    }

    private String type=null;

    /**
     * 确定文件类型
     * @return
     */
    @Override
    public String type() {
        if (type!=null) return type;
        String nm=file.getName();
        if (nm.endsWith(".html")||nm.endsWith(".htm")){
            type="text/html;charset=ios-8859-1";
        }else if (nm.indexOf('.')<0||nm.endsWith(".txt")){
            type="text/plain;charset=ios-8859-1";
        }else {
            type="application/octet-stream";
        }
        return type;
    }

    private FileChannel fileChannel=null;
    private long length=-1;
    private long position=-1;

    @Override
    public long length() {
        return length;
    }

    /**
     * 创建FileChannel对象
     * @throws IOException
     */
    @Override
    public void prepare() throws IOException {
        if (fileChannel==null){
            fileChannel=new RandomAccessFile(file,"r").getChannel();
        }
        length=fileChannel.size();
        position=0;
    }

    /**
     * 发送正文，如果发送完毕，就返回false，否则就返回true
     * @param cio 需要进行操作的通道
     * @return
     * @throws IOException
     */
    @Override
    public boolean send(ChannelIO cio) throws IOException {
        if (fileChannel==null){
            throw new IllegalStateException();
        }
        if (position<0){
            throw new IllegalStateException();
        }
        if (position>=length){
            return false;
        }
        position+=cio.transferTo(fileChannel,position,length-position);
        return (position<length);
    }

    @Override
    public void release() throws IOException {
        if (fileChannel!=null){
            fileChannel.close();
            fileChannel=null;
        }
    }
}
