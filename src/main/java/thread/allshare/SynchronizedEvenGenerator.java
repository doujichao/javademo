package thread.allshare;

public class SynchronizedEvenGenerator extends IntGenerator{
    private int currentEvenValue =0;
    @Override
    public synchronized int next() {
        ++currentEvenValue; //Danger point here
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new SynchronizedEvenGenerator());
    }
}
