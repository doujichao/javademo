package 网络编程和IO.socket.serversocket;

public class ThreadPoolTest {

    public static void main(String[] args){
        //创建线程池
        ThreadPool threadPool=new ThreadPool("工作组");
        for (int i=0;i<10;i++){
            threadPool.execute(createTask(i));
        }

        threadPool.join();

    }

    /**
     * 定义一个简单的任务
     * @param i
     * @return
     */
    private static Runnable createTask(final int i) {
        return () -> {
            System.out.println("Task "+i+" start:");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Task "+i+" end:");
            }
        };
    }
}
