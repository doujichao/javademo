package 网络编程和IO.socket.serversocket;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor.execute()
 * ExecutorService.shutdown()
 * ExecutorService.awaitTermination()
 * ExecutorService.isTermination()
 *
 * newCachedThreadPool() 再有任务时才创建新线程，空闲线程被保留60秒
 * newFixedThreadPool(int nThreads)：线程池中包含固定数目的线程，空闲线程会一直保留
 *                  参数nThreads设定线程池中线程的数量
 * newSingleThreadExecutor():线程池中只有一个工作线程，一次执行每个任务
 * newScheduledThreadPool(int corePoolSize)：线程池能按照计划来执行任务，允许用户设定，
 *                          执行计划的时间，当任务较多的时候，线程池可能会创建更多的工作线程来执行任务
 * newSingleThreadScheduleExecutor():线程池中只有一个工作线程，它能按时间计划来执行任务
 *
 */
public class ExecutorTest {

    @Test
    public void test(){
        ExecutorService pool =
                Executors.newCachedThreadPool();
        System.out.println(pool);
    }
}
