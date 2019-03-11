package thread.newcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueDemo {

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> queue=new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue,exec));
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}

class PrioritizedTask implements Runnable,Comparable<PrioritizedTask>{

    private Random random=new Random(47);
    private static int counter=0;
    private final int id=counter++;
    private final int priority;
    protected static List<PrioritizedTask> sequence=new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }


    @Override
    public int compareTo(PrioritizedTask o) {
        return priority < o.priority ? 1 : (priority > o.priority ? -1 : 0);
    }

    public String sumary(){
        return "("+id+" : "+priority+")";
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]",priority)+" task "+id;
    }

    public static class EndSentinel extends PrioritizedTask{

        private ExecutorService exec;
        public EndSentinel(ExecutorService e) {
            super(-1);
            exec=e;
        }

        @Override
        public void run() {
            int count=0;
            for (PrioritizedTask pt:sequence){
                System.out.println(pt.sumary());
                if (++count % 5 ==0){
                    System.out.println();
                }
                System.out.println();
                System.out.println(this+" Calling shutdownNow()");
                exec.shutdown();
            }
        }
    }
}

class PrioritizedTaskProducer implements Runnable{

    private Random random=new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;
    public PrioritizedTaskProducer(Queue<Runnable> queue,ExecutorService exec){
        this.queue=queue;
        this.exec=exec;
    }
    @Override
    public void run() {
        for (int i=0;i< 20;i++){
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }
        try {
            for (int i=0;i<10;i++){
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            for (int i=0;i<10;i++){
                queue.add(new PrioritizedTask(i));
            }
            queue.add(new PrioritizedTask.EndSentinel(exec));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable{
    private PriorityBlockingQueue<Runnable> q;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                q.take().run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}