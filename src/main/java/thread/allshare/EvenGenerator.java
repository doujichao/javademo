package thread.allshare;

public class EvenGenerator  extends IntGenerator{
    private int currentEvenValue =0;
    @Override
    public int next() {
        ++currentEvenValue; //Danger point here
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new EvenGenerator());
    }
}
