package thread.allshare;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MuterEvenGenerator extends IntGenerator{
    private int currentEvenValue =0;
    private Lock lock=new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue; //Danger point here
            Thread.yield();
            ++currentEvenValue;
            return currentEvenValue;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EventChecker.test(new MuterEvenGenerator());
    }
}
