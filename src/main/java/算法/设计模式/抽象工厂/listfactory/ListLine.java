package 算法.设计模式.抽象工厂.listfactory;

import 算法.设计模式.抽象工厂.factory.Link;

public class ListLine extends Link {
    public ListLine(String caption, String url) {
        super(caption,url);
    }

    @Override
    public String makeHtml() {
        return "<li><a href=\""+url+"\">"+caption+"</a></li>\n";
    }
}
