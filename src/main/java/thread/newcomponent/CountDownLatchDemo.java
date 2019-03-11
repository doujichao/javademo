package thread.newcomponent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch可以设定被等待任务执行的个数，而等待任务调用await方法直到初始值变为0
 * 才会执行等待方法
 */
public class CountDownLatchDemo {

    static final int SIZE=100;
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch=new CountDownLatch(SIZE);
        for (int i=0;i<10;i++){
            exec.execute(new WaitingTask(latch));
        }
        for (int i=0;i<SIZE;i++){
            exec.execute(new TaskPortTion(latch));
        }
        System.out.println("Launched all tasks");
        exec.shutdown();
    }

    @Test
    public void test(){
        System.out.println(new TaskPortTion(new CountDownLatch(2)));
    }
}

class TaskPortTion implements Runnable{
    private static  int counter =0 ;
    private final int id = counter++;
    private static Random random=new Random(47);
    private final CountDownLatch latch;

    TaskPortTion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String toString() {
        return String.format("%1$-3d",id);
    }


    public void doWord() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this + "completed");
    }

    @Override
    public void run() {
        try {
            doWord();
            latch.countDown();
        } catch (InterruptedException e) {

        }
    }
}


class WaitingTask implements Runnable{
    private static  int counter =0 ;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d",id);
    }



    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Latch barrier passed for "+this);
        } catch (InterruptedException e) {
            System.out.println(this+" interrupted");
        }
    }
}