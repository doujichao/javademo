package 算法.设计模式.策略模式;

/**
 * 出拳策略
 */
public interface Strategy {
    Hand nextHand();

    /**
     * 检测上一局是否获胜
     * @param win 上一局是否获胜
     */
    void study(boolean win);
}
