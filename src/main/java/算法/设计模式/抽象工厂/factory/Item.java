package 算法.设计模式.抽象工厂.factory;

/**
 * 表示项目类
 */
public abstract class Item {
    /**标题,内容*/
    protected String caption;

    public Item(String caption) {
        this.caption = caption;
    }

    public abstract String makeHtml();
}
