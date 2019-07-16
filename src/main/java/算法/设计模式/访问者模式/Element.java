package 算法.设计模式.访问者模式;

/**
 * 接受访问的类
 */
public interface Element {

    void accept(Visitor v);
}
