package 算法.设计模式.建造者模式;

/**
 * 编写文档的方法的抽象类
 */
public abstract class Builder {
    /**
     * 编写标题
     * @param title 标题
     */
    public abstract void makeTitle(String title);

    /**
     * 编写字符串
     * @param str 字符串
     */
    public abstract void makeString(String str);

    /**
     * 编写条目
     * @param items 条目
     */
    public abstract void makeItems(String[] items);

    /**
     * 完成文档编写
     */
    public abstract void close();
}
