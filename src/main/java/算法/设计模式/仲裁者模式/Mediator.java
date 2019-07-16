package 算法.设计模式.仲裁者模式;

/**
 * 表示仲裁者的接口
 */
public interface Mediator {
    //创建同事的实例
    void createColleagues();
    //中心调控器
    void colleagueChanged();
}
