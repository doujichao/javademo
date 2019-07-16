package 算法.设计模式.抽象工厂.factory;

/**
 * 抽象的表示Html的超链接类
 */
public abstract class Link extends Item{
    protected String url;
    public Link(String caption,String url) {
        super(caption);
        this.url=url;
    }
}
