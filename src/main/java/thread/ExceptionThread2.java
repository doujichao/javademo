package thread;

import java.util.concurrent.ThreadFactory;

public class ExceptionThread2 implements Runnable{
    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("run() by"+thread);
        System.out.println("eh="+thread.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }

    class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("caught "+e);
        }
    }

    class HandlerThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            System.out.println(this+" creating new Thread");
            Thread t=new Thread(r);
            System.out.println("created "+t);
            t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            System.out.println("en = "+t.getUncaughtExceptionHandler());
            return t;
        }
    }
}
