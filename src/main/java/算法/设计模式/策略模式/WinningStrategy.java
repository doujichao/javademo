package 算法.设计模式.策略模式;

import java.util.Random;

public class WinningStrategy implements Strategy{

    private Random random;
    /**
     * 上一局的输赢
     */
    private boolean won=false;
    private Hand prevHand;
    public WinningStrategy(int seed){
        random=new Random(seed);
    }

    @Override
    public Hand nextHand() {
        if (!won){
            prevHand=Hand.getHand(random.nextInt(3));
        }
        return prevHand;
    }

    @Override
    public void study(boolean win) {
        won=win;
    }
}
