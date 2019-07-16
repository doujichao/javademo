package 算法.设计模式.策略模式;

/**
 * 进行猜拳的选手类
 */
public class Player {

    /**选手名称*/
    private String name;
    /**策略*/
    private Strategy strategy;
    /**赢的次数*/
    private int wincount;
    /**输的次数*/
    private int lostcount;
    /**游戏次数*/
    private int gamecount;
    public Player(String name,Strategy strategy){
        this.name=name;
        this.strategy=strategy;
    }

    public Hand nextHand(){
        return strategy.nextHand();
    }

    /**
     * 赢
     */
    public void win(){
        strategy.study(true);
        wincount++;
        gamecount++;
    }

    /**
     * 输
     */
    public void lose(){
        strategy.study(false);
        lostcount++;
        gamecount++;
    }

    /**
     * 平局
     */
    public void even(){
        gamecount++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", strategy=" + strategy +
                ", wincount=" + wincount +
                ", lostcount=" + lostcount +
                ", gamecount=" + gamecount +
                '}';
    }
}
