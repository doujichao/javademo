package thread.newcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

public class DelayQueueDemo {

    public static void main(String[] args){
        Random random=new Random(47);
        ExecutorService exec=Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue=new DelayQueue<>();
        for (int i=0;i<20;i++){
            queue.put(new DelayedTask(random.nextInt(5000)));
        }
        queue.add(new DelayedTask.Essential(5000,exec));
        exec.execute(new DelayedConsumer(queue));
    }
}

class DelayedConsumer implements Runnable{
    private DelayQueue<DelayedTask> q;

    public DelayedConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                q.take().run();
            }
        } catch (InterruptedException e) {
        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}


class DelayedTask implements Runnable, Delayed{

    private static int counter=0;
    private final int id=counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence=new ArrayList<>();
    public DelayedTask(int delta){
        this.delta=delta;
        trigger=System.nanoTime()+NANOSECONDS.convert(delta,MILLISECONDS);
        sequence.add(this);
    }



    @Override
    public int compareTo(Delayed o) {
        DelayedTask that= (DelayedTask) o;
        if (trigger < that.trigger)return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    public String summary(){
        return "(" + id+":"+delta+")";
    }

    public static class Essential extends DelayedTask{

        private ExecutorService exec;

        public Essential(int delta, ExecutorService e) {
            super(delta);
            exec=e;
        }

        @Override
        public void run() {
            for (DelayedTask pt:sequence){
                System.out.println(pt.summary()+" ");
            }
            System.out.println(this+" Calling shutdownNow()");
            exec.shutdown();
        }
    }



    @Override
    public String toString() {
        return String.format("[%1$-4d]",delta)+"Task "+id;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger-System.nanoTime(),NANOSECONDS);
    }
}