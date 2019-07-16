package 算法.设计模式.策略模式;

import java.util.Random;

/**
 * 实验策略
 */
public class ProbStrategy implements Strategy {

    private Random random;

    private int prevHandValue=0;

    private int currnetHandValue=0;

    private int[][] history={
            {1,1,1},
            {1,1,1},
            {1,1,1}
    };

    public ProbStrategy(int seed){
        random=new Random(seed);
    }

    @Override
    public Hand nextHand() {
        int bet = random.nextInt(getSum(currnetHandValue));
        int handvalue=0;
        if (bet<history[currnetHandValue][0]){
            handvalue=0;
        }else if(bet<history[currnetHandValue][0]+history[currnetHandValue][1]){
            handvalue=1;
        }else {
            handvalue=2;
        }
        prevHandValue=currnetHandValue;
        currnetHandValue=handvalue;
        return Hand.getHand(handvalue);
    }

    private int getSum(int hv) {
        int sum=0;
        for (int i=0;i<3;i++){
            sum+=history[hv][i];
        }
        return sum;
    }

    @Override
    public void study(boolean win) {
        if (win){
            //如果赢的话，当前数值加一
            history[prevHandValue][currnetHandValue]++;
        }else {
            //如果输的话，其他两个加一
            history[prevHandValue][(currnetHandValue+1)%3]++;
            history[prevHandValue][(currnetHandValue+2)%3]++;
        }
    }
}
