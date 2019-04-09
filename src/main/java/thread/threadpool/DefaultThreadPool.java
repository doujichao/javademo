package thread.threadpool;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    /**
     * 线程最大限制数
     */
    private static final int MAX_WORKER_NUMBERS=10;
    /**
     * 线程池默认的数量
     */
    private static final int DEFAULT_WORKER_NUMBERS=5;

    /**
     * 线程池最小的数量
     */
    private static final int MIN_WORKER_NUMBERS=1;

    /**
     * 这是一个工作列表，将会向里面插入工作
     */
    private final LinkedList<Job> jobs=new LinkedList<>();

    private final List<Worker>  workers= Collections.synchronizedList(new ArrayList<>());

    /**
     * 工作线程数量
     */
    private int workerNum=DEFAULT_WORKER_NUMBERS;

    /**
     * 线程编号生成
     */
    private AtomicLong threadNum=new AtomicLong();

    public DefaultThreadPool(){
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num){
        //这里取的是num，最大值和最小值的中间值
        workerNum = num > MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:
                num<MIN_WORKER_NUMBERS?MIN_WORKER_NUMBERS:num;
        initializeWorkers(workerNum);
    }

    /**
     * 初始化工作线程
     * @param num
     */
    private void initializeWorkers(int num){
        for (int i=0;i<num;i++){
            Worker worker=new Worker();
            workers.add(worker);
            Thread thread=new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job!=null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            //限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS){
                num=MAX_WORKER_NUMBERS-this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum+=num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if (num >= this.workerNum){
                throw new IllegalStateException("beyond workNum");
            }
            //按照给定的数量停止Worker
            int count=0;
            while (count < num){
                Worker worker = workers.get(count);
                if (workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 工作者，负责消费任务
     */
    class Worker implements Runnable{

        /**
         * 是否在工作，使用volatile保证内存可见性
         */
        private volatile boolean running=true;

        @Override
        public void run() {
            while (running){
                Job job=null;
                //等待唤醒模式
                synchronized (jobs){
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    //取出一个job
                    job=jobs.removeFirst();
                }
                if (job!=null){
                    try {
                        job.run();
                    }catch (Exception e){

                    }
                }
            }
        }

        public void shutdown(){
            running=false;
        }
    }
}
