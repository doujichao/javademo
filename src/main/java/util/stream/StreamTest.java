package util.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class StreamTest {

    private static List<Integer> list1=new ArrayList<>();
    private static List<Integer> list2=new ArrayList<>();
    private static List<Integer> list3=new ArrayList<>();
    private static Lock lock=new ReentrantLock();

    @Test
    public void testParallelStream(){
        IntStream.range(0,10000).forEach(list1::add);
        IntStream.range(0,10000).parallel().forEach(list2::add);
        IntStream.range(0,10000).forEach(i->{
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });
        System.out.println("串行执行的大小："+list1.size());
        System.out.println("并行执行的大小："+list2.size());
        System.out.println("加锁并执行的大小："+list3.size());
    }
}
