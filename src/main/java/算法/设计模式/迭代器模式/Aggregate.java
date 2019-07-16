package 算法.设计模式.迭代器模式;

/**
 * 指代集合的接口，实现该接口，代表类为集合
 */
public interface Aggregate {
    /**
     * 该方法会生成一个用于遍历集合的迭代器
     * @return
     */
    Iterator iterator();

}
