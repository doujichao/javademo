package thread.garden;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Entrance implements Runnable{

    private static Count count=new Count();
    private static List<Entrance> entrances=new ArrayList<>();
    private int number=0;

    private final int id;
    private static volatile boolean canceled=false;
    private static void cancel(){canceled=true;}

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled){
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: "+count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }

        System.out.println("Stopping "+this);
    }

    public synchronized int getValue(){return number;}

    @Override
    public String toString() {
        return "Entrance "+id+": "+getValue();
    }

    public static int getTotalCount(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum=0;
        for (Entrance entrance:entrances){
            sum+=entrance.getValue();
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec= Executors.newCachedThreadPool();
        for (int i=0;i < 5;i++){
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        if (!exec.awaitTermination(250,TimeUnit.MILLISECONDS)){
            System.out.println("Some task were not terminated");
        }
        System.out.println("Total:"+Entrance.getTotalCount());
        System.out.println("Sum of Entrances: "+Entrance.sumEntrances());
    }
}
