package thread.pro2conmodel;


import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ToastOMatic {
}

class Toast{
    public enum Status{DRY,BUTTERED,JAMMED}
    private Status status=Status.DRY;
    private final int id;
    public Toast(int id) {
        this.id = id;
    }
    public void butter(){status=Status.BUTTERED;}
    public void jam(){status=Status.JAMMED;}
    public Status getStatus(){return status;}
    public int getId(){return id;}

    @Override
    public String toString() {
        return "Toast "+id+": "+status;
    }
}

class ToastQueue extends LinkedBlockingDeque<Toast>{

}

class Toaster implements Runnable{

    private ToastQueue toastQueue;
    private int count=0;
    private Random rand=new Random(47);
    public Toaster (ToastQueue toastQueue){
        this.toastQueue=toastQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100+rand.nextInt(500));
                Toast t=new Toast(count++);
                System.out.println(t);
                toastQueue.put(t);
            }

        } catch (InterruptedException e) {
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}

class Eater implements Runnable{

    private ToastQueue finishedQueue;
    private int count=0;
    private Random rand=new Random(47);
    public Eater (ToastQueue toastQueue){
        this.finishedQueue=toastQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast t = finishedQueue.take();
                if (t.getId() != count++ || t.getStatus()!=Toast.Status.JAMMED){
                    System.out.println(">>>>Error:"+t);
                    System.exit(1);
                }else {
                    System.out.println("Chomp!"+t);
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}

/**
 * 奶油加工
 */
class Bufferer implements Runnable{

    private ToastQueue dryQueue,butteredQueue;

    public Bufferer(ToastQueue dry,ToastQueue buttered){
        dryQueue=dry;
        butteredQueue=buttered;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(t);
                butteredQueue.put(t);
            }

        } catch (InterruptedException e) {
            System.out.println("Buttered interrupted");
        }
        System.out.println("Buttered off");
    }
}


/**
 * 奶油加工
 */
class Jammer implements Runnable{

    private ToastQueue butteredQueue,finishedQueue;

    public Jammer(ToastQueue dry,ToastQueue finished){
        butteredQueue=dry;
        finishedQueue=finished;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.put(t);
            }

        } catch (InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}