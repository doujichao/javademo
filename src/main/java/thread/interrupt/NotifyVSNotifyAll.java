package thread.interrupt;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotifyVSNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0;i < 5;i++){
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod=true;
            @Override
            public void run() {
                if (prod){
                    System.out.println("notify()");
                    Task.blocker.prod();
                    prod=false;
                }else {
                    System.out.println("notifyAll()");
                    Task.blocker.prodAll();
                    prod=true;
                }
            }
        },400,400);

        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("Timer canceled");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Task2.blocker.proAll()");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("shutting down");
        exec.shutdown();
    }
}

class Blocker{
    synchronized void waitingCall(){
        try {
            while (!Thread.interrupted()){
                wait();
                System.out.println(Thread.currentThread()+" ");
            }
        } catch (InterruptedException e) {

        }
    }

    synchronized void prod(){notify();}
    synchronized void prodAll(){notifyAll();}
}

class Task implements Runnable{
    static Blocker blocker=new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable{
    static Blocker blocker=new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}