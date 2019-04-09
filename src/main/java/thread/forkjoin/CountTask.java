package thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    //阈值
    private static final int THRESHOLD=2;
    private int start;
    private int end;
    @Override
    protected Integer compute() {
        int sum=0;
        //如果任务足够小就计算任务
        boolean canCompulte = (end - start) <= THRESHOLD;
        if (canCompulte){
            for (int i=start;i<=end;i++){
                sum+=i;
            }
        }else {
            //如果任务大于阈值，就分裂成两个子任务计算
            int middle=(start + end )/2;
            //将任务分解
            CountTask leftTask=new CountTask(start,middle);
            CountTask rightTask=new CountTask(middle+1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，并得到其结果
            int leftResult=leftTask.join();
            int rightResut=rightTask.join();
            //合并子任务
            sum=leftResult+rightResut;
        }
        return sum;
    }

    public static void main(String[] args){
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4
        CountTask task=new CountTask(1,100000);
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public CountTask(int start,int end){
        this.start=start;
        this.end=end;
    }
}
