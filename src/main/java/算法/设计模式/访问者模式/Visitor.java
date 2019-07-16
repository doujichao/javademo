package 算法.设计模式.访问者模式;

/**
 * 访问者的抽象类
 */
public abstract class Visitor {

    public abstract void visit(File file);
    public abstract void visit(Directory directory);

}
