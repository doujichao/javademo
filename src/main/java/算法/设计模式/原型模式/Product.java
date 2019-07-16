package 算法.设计模式.原型模式;

/**
 * 复制功能的接口
 */
public interface Product extends Cloneable {

    void use(String s);

    Product createClone();

}
