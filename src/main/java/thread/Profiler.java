package thread;

import java.util.concurrent.TimeUnit;

public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL=new ThreadLocal<Long>();

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
//        TIME_THREADLOCAL.get();
        TimeUnit.SECONDS.sleep(1);
        System.out.printf("Cost:"+Profiler.end()+"mills");
    }
}
