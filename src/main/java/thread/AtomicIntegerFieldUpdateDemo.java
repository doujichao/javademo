package thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdateDemo {

    public static class Candidate{
        int i;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdate
            =AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");

    //检查updater是否正常工作
    public static AtomicInteger allScore=new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        final Candidate stu=new Candidate();
        Thread[] t=new Thread[1000];
        for (int i=0;i<1000;i++){
            t[i]=new Thread(() ->{
                if (Math.random()>0.4){
                    scoreUpdate.incrementAndGet(stu);
                    allScore.incrementAndGet();
                }
            });
            t[i].start();
        }
        for (int i=0;i<1000;i++){t[i].join();}
        System.out.println(stu.score);
        System.out.println(allScore);
    }

}
