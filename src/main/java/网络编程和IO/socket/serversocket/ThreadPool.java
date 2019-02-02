package 网络编程和IO.socket.serversocket;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup{
    /**线程池是否关闭*/
    private boolean isClosed=false;
    /**表示工作队列*/
    private LinkedList<Runnable> workQueue;
    /**表示线程id*/
    private static int threadPoolID;
    /**表示工作线程id*/
    private int threadID;

    public ThreadPool(String name) {
        super(name);
        setDaemon(true);
        //创建工作队列
        workQueue=new LinkedList<>();
        //创建并启动工作线程
        for (int i=0;i<10;i++){
            new WorkThread().start();
        }
    }

    /**
     * 向工作队列中加入一个新任务，有工作线程区去执行该任务
     * @param task
     */
    public synchronized void execute(Runnable task){
        //如果线程池被关闭则抛出IllegalStateException异常
        if(isClosed){
            throw new IllegalStateException("线程池被关闭");
        }
        if (task!=null){
            workQueue.add(task);
            //唤醒正在getTask()方法中等待任务的工作线程
            notify();
        }
    }

    /**
     * 共工作队列中取出一个任务，工作线程会调用此方法
     * @return
     * @throws InterruptedException
     */
    public synchronized Runnable getTask() throws InterruptedException{
        while (workQueue.size()==0){
            if (isClosed){
                return null;
            }
            //如果工作队列中没有任务，就等待任务
            wait();
        }
        return workQueue.removeFirst();
    }

    /**
     * 关闭线程池
     */
    public synchronized void close(){
        if(!isClosed){
            isClosed=true;
            //清空工作队列
            workQueue.clear();
            //中断线程
            interrupt();
        }
    }

    /**
     * 等待工作线程把所有任务执行完
     */
    public void join(){
        synchronized (this){
            isClosed=true;
            //唤醒孩子getTask()方法中等待任务的工作现车给
            notifyAll();
        }
        Thread[] threads=new Thread[activeCount()];
        //enumerate方法继承子ThreadGroup类，获得线程族中当前所有或者的工作线程
        int count=enumerate(threads);
        //等待所有工作线程运行结束
        for (int i=0;i<count;i++){
            try {
                //等待工作线程运行结束
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * 内部类：工作线程
     */
    private class WorkThread extends Thread{
        public WorkThread(){
            //加入到当前ThreadPool线程组中
            super(ThreadPool.this,"WorkThread-"+(threadID++));
        }

        @Override
        public void run() {
            //isInterrupted方法继承自Thread，判断线程是否被中断
            while (!isInterrupted()){
                Runnable task=null;
                try {
                    task=getTask();
                } catch (InterruptedException e) {
                }
                //如果getTask()返回null或者线程执行getTask时被中断，就此结束此线程
                if (task==null){
                    return;
                }

                //运行任务，异常再catch代码块中被捕获
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
