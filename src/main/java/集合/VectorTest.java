package 集合;

import java.util.Vector;

/**
 * 虽然Vector是线程安全的，但是还是需要进行同步，因为有可能出现删除一个元素之后，另一个线程获取该元素
 */
public class VectorTest {

    private static Vector<Integer> vector=new Vector<>();

    public static void main(String[] args){
        while (true){
            for (int i=0;i<10;i++){
                vector.add(i);
            }

            Thread removeThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<vector.size();i++){
                        vector.remove(i);
                    }
                }
            });
            Thread printThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<vector.size();i++){
                        System.out.println(vector.get(i));
                    }
                }
            });
            removeThread.start();
            printThread.start();
            while (Thread.activeCount() >5);
        }
    }
}
