package thread.allshare;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocing {

    private ReentrantLock lock=new ReentrantLock();

    public void untimed(){
        boolean captrued = lock.tryLock();
        try {
            System.out.println("tryLock():"+captrued);
        }finally {
            if (captrued){
                lock.unlock();
            }
        }
    }

    public void timed(){
        boolean captured=false;
        try {
            captured = lock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("tryLock(2,TimeUnit.SECONDS)： "+captured);
        }finally {
            if (captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        final AttemptLocing al=new AttemptLocing();
        al.untimed();
        al.timed();
        new Thread(){
            //代码块
            {setDaemon(true);}

            @Override
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();
        al.untimed();
        al.timed();
    }
}
