package thread.newcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {

    static final int FINISHI_LINE=75;
    private List<Horse> horses=new ArrayList<>();
    private ExecutorService exec= Executors.newCachedThreadPool();
    public CyclicBarrierDemo(){}
}

class Horse implements Runnable{
    private static int counter=0;
    private final int id=counter++;
    private int strides=0;
    private static Random random=new Random(47);
    private static CyclicBarrier barrier;
    public Horse(CyclicBarrier barrier){
        this.barrier=barrier;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    strides += random.nextInt(3);
                }
                barrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Horse "+id+" ";
    }

    public String tracks(){
        StringBuilder s=new StringBuilder();
        for (int i=0;i<getStrides();i++){
            s.append("*");
        }
        s.append("id");
        return s.toString();
    }

    public synchronized int getStrides() {
        return strides;
    }
}