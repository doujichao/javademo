package 算法.设计模式.仲裁者模式;

/**
 * 向仲裁者进行报告的组员的接口
 */
public interface Colleague {
    //添加调停者
    void setMediator(Mediator mediator);
    //设置本身应该的动作
    void action(boolean enabled);
}
