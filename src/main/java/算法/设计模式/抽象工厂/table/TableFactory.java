package 算法.设计模式.抽象工厂.table;

import 算法.设计模式.抽象工厂.factory.Factory;
import 算法.设计模式.抽象工厂.factory.Link;
import 算法.设计模式.抽象工厂.factory.Page;
import 算法.设计模式.抽象工厂.factory.Tray;

public class TableFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new TableLink(caption,url);
    }

    @Override
    public Tray createTray(String caption) {
        return new TableTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new TablePage(title,author);
    }
}
