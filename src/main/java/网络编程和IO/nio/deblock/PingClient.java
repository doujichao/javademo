package 网络编程和IO.nio.deblock;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class PingClient {
    private Selector selector;
    //存放用户新提交的任务
    private LinkedList targets=new LinkedList();
    //存放已经完成的需要答应的任务
    private LinkedList finishedTargets=new LinkedList();

    public PingClient () throws IOException{
        selector=Selector.open();
        Connector connector=new Connector();
        Printer printer=new Printer();
    }

    private class Printer extends Thread{

    }

    private class Connector extends Thread{
    }
}

/**
 * 表示任何一项任务
 */
class Target{
    InetSocketAddress address;
    SocketChannel channel;
    Exception failure;
    /**开始连接时间*/
    long connectStart;
    /**连接成功时的时间*/
    long connectFinish=0;
    /**该任务是否已经打印了*/
    boolean shown=false;

    Target(String host){
        try {
            address=new InetSocketAddress(InetAddress.getByName(host),80);
        } catch (UnknownHostException e) {
            failure=e;
        }
    }

    void show(){
        String result;
        if (connectFinish!=0){
            result=Long.toString(connectFinish-connectFinish)+"ms";
        }else if (failure!=null){
            result=failure.toString();
        }else {
            result="Timed out";
        }

        System.out.println(address+":"+result);
        shown=true;
    }
}
