package 算法.设计模式.适配器模式;

/**
 * 需求接口
 */
public interface Print {

    /**
     * 弱版字符
     */
    void printWeak();

    /**
     * 加强字符
     */
    void printStrong();

}
