package 算法.设计模式.状态模式;

public interface Context {

    /**
     * 设置时间
     * @param hour
     */
    void setClock(int hour);

    /**
     * 改变状态
     * @param instance
     */
    void changeState(State instance);

    /**
     * 联系报警中心
     * @param s
     */
    void recordLog(String s);

    /**
     * 在报警中心留下记录
     * @param s
     */
    void callSecurityCenter(String s);
}
