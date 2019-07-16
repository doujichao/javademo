package 算法.设计模式.抽象工厂.listfactory;

import 算法.设计模式.抽象工厂.factory.Factory;
import 算法.设计模式.抽象工厂.factory.Link;
import 算法.设计模式.抽象工厂.factory.Page;
import 算法.设计模式.抽象工厂.factory.Tray;

/**
 * listFactory具体实现类
 */
public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLine(caption,url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title,author);
    }
}
