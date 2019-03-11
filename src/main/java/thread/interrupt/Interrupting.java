package thread.interrupt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Interrupting {
    private static ExecutorService exec= Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        Future<?> submit = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupt sent to "+ r.getClass().getName());
        submit.cancel(true);
        System.out.println("Interrupt sent to "+r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}
