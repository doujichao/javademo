package thread.lock;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairTest {
    //公平锁
    private static Lock fairLock=new ReentrantLock2(true);
    //非公平锁
    private static Lock unfairLock=new ReentrantLock2(false);

    @Test
    public void fair(){
        testLock(fairLock);
    }

    @Test
    public void unFair(){
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        for (int i=0;i<5;i++){
            new Job(lock,i+"").start();
        }
    }

    private static class Job extends Thread{
        private Lock lock;
        public Job(Lock lock,String name){
            super(name);
            this.lock=lock;
        }

        @Override
        public void run() {
            ReentrantLock2 lock2= (ReentrantLock2) lock;
            for (int j=0;j<3;j++){
                try {
                    int i=0;
                    lock.lock();
                    Collection<Thread> list = lock2.getQueuedThread();
                    Iterator<Thread> iterator = list.iterator();
                    System.out.print("Lock by ["+Thread.currentThread().getName()+"]   ");
                    System.out.print("Waiting by [");

                    while (iterator.hasNext()){
                        Thread thread = iterator.next();
                        if (i!=0){
                            System.out.print(",");
                        }
                        i++;
                        System.out.print(thread.getName());
                    }
                    System.out.println("]");
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{

        public ReentrantLock2(boolean b) {
            super(b);
        }

        public Collection<Thread> getQueuedThread(){
            List<Thread> arrayList=new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
