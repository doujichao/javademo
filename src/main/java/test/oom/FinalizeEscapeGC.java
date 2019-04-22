package test.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 此代码演示了两点：
 * 1、对象可以在被GC时自我拯救
 * 2、这种自救的机会只有一次，因为一个对象的finalize方法最多
 * 只会被系统自动调用一次
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK=null;

    public void isAlive(){
        System.out.println("yes,i am still alive:");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK=this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK=new FinalizeEscapeGC();
        //对象第一次成功拯救自己
        SAVE_HOOK=null;
        System.gc();
        //因为Finalizer方法优先级很低，暂停1秒，以等待他
        TimeUnit.SECONDS.sleep(1);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead :(");
        }
        //下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK=null;
        System.gc();
        //因为Finalizer方法优先级很低，暂停1秒，以等待他
        TimeUnit.SECONDS.sleep(1);
        if (SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead :(");
        }
    }

    private static final int _1MB =1024*1024;

    @Test
    public void testAllocation(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 =new byte[2 * _1MB];
        allocation2 =new byte[2 * _1MB];
        allocation3 =new byte[2 * _1MB];
        allocation4 =new byte[2 * _1MB];
    }

    @Test
    public void testTenuringThreshold(){
        byte[] allocation1,allocation2,allocation3;
        allocation1 =new byte[ _1MB / 4];
        allocation2 =new byte[4 * _1MB];
        allocation3 =new byte[4 * _1MB];
        allocation3 =null;
        allocation3 =new byte[4 * _1MB];
    }

    public byte[] placehodler =new byte[64*1024];

    @Test
    public void testFillHeap() throws InterruptedException {
        List<HeapOOM.OOMObject> list=new ArrayList<>();
        for (int i=0;i<1000;i++){
            Thread.sleep(50);
            list.add(new HeapOOM.OOMObject());
        }
        System.gc();
    }

    /**
     * 线程死循环
     */
    @Test
    public void createBusyThread(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true);
            }
        },"testBusyThread");
        thread.start();
    }

}
