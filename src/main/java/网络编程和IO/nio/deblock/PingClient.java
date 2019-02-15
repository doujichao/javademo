package 网络编程和IO.nio.deblock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

public class PingClient {
    private Selector selector;
    /**存放用户新提交的任务*/
    private final LinkedList targets=new LinkedList();
    /**存放已经完成的需要答应的任务*/
    private LinkedList finishedTargets=new LinkedList();

    public PingClient () throws IOException{
        selector=Selector.open();
        Connector connector=new Connector();
        Printer printer=new Printer();
        //启动连接线程
        connector.start();
        //启动打印线程
        printer.start();
        //主线程接收用户从控制台输入的主机名，然后提交Target
        receiveTarget();
    }


    public static void main(String[] args) throws IOException {
        new PingClient();
    }

    public void addTarget(Target target){
        //向targets队列中加入一个任务，主线程会调用该方法
        SocketChannel socketChannel=null;
        try {
            socketChannel=SocketChannel.open();
            //设置非阻塞通道
            socketChannel.configureBlocking(false);
            socketChannel.connect(target.address);
            //给target赋值
            target.channel=socketChannel;
            target.connectStart=System.currentTimeMillis();

            synchronized (targets){
                targets.add(target);
            }
            selector.wakeup();

        }catch (Exception e){
            if (socketChannel!=null){
                try{
                    socketChannel.close();
                }catch (IOException ee){}
                target.failure=e;
                addFinishedTarget(target);
            }
        }
    }

    private void addFinishedTarget(Target target) {
        //向finishedTargets队列中加入一个任务，主线程和Connector线程会调用该方法
        synchronized (finishedTargets){
            finishedTargets.notify();
            finishedTargets.add(target);
        }
    }

    public void printFinishTargets(){
        //打印finishedTargets队列中的任务，Printer线程会调用该方法
        try {
            for (;;){
                Target target=null;
                synchronized (finishedTargets){
                    while (finishedTargets.size()==0){
                        finishedTargets.wait();
                    }
                    target= (Target) finishedTargets.removeFirst();
                }
                target.show();
            }
        }catch (InterruptedException x){
            return;
        }
    }

    public void registerTargets(){
        //取出targets队列中的任务，向Selector注册连接就绪时间，Connector线程就会调用该方法
        synchronized (targets){
            while (targets.size()>0){
                Target target= (Target) targets.removeFirst();
                try {
                    target.channel.register(selector, SelectionKey.OP_CONNECT,target);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                    try {
                        target.channel.close();
                    } catch (IOException e1) {e1.printStackTrace();}
                    target.failure=e;
                    addFinishedTarget(target);
                }
            }
        }
    }

    public void processSelectedKeys(){
        //处理连接就绪事件，Connector线程会调用该方法
        for (Iterator it=selector.selectedKeys().iterator(); it.hasNext();){
            SelectionKey selectionKey= (SelectionKey) it.next();
            it.remove();

            Target target= (Target) selectionKey.attachment();
            SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
            try {
                if (socketChannel.finishConnect()){
                    //将当前的key取消
                    selectionKey.cancel();
                    target.connectFinish=System.currentTimeMillis();
                    socketChannel.close();
                    addFinishedTarget(target);
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socketChannel.close();
                } catch (IOException e1) {}
                target.failure=e;
                addFinishedTarget(target);
            }

        }
    }

    /**用于控制Connector线程*/
    boolean shutdown=false;

    private void receiveTarget() {
        //接收用户输入的域名，向targets队列中加入任务，主线程会调用该方法
        try {
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while ((msg=localReader.readLine())!=null){
                if (!msg.equals("bye")){
                    Target target=new Target(msg);
                    addTarget(target);
                }else {
                    shutdown=true;
                    selector.wakeup();
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 负责从finishedTargets队列中取出Target对象
     */
    private class Printer extends Thread{
        public Printer(){
            //设为设为守护进程
            setDaemon(true);
        }

        @Override
        public void run() {
            printFinishTargets();
        }
    }

    /**
     * 处理连接就绪事件
     */
    private class Connector extends Thread{
        @Override
        public void run() {
            while (!shutdown){
                try {
                    registerTargets();
                    if (selector.select()>0){
                        processSelectedKeys();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            result= (connectFinish - connectFinish) +"ms";
        }else if (failure!=null){
            result=failure.toString();
        }else {
            result="Timed out";
        }

        System.out.println(address+":"+result);
        shown=true;
    }
}
