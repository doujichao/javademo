package 算法.设计模式.迭代器模式;

/**
 * 该类指示可以被迭代,代表可以被遍历的元素
 */
public interface Iterator {

    /**
     * 是否存在下一个元素
     * @return
     */
    boolean hasNext();

    /**
     * 返回集合中的一个元素
     * @return
     */
    Object next();

}
