package 算法.设计模式.抽象工厂.table;

import 算法.设计模式.抽象工厂.factory.Item;
import 算法.设计模式.抽象工厂.factory.Page;

import java.util.Iterator;

public class TablePage extends Page {


    public TablePage(String title, String author) {
        super(title,author);
    }

    @Override
    protected String makeHtml() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("<html><head><title>"+title+"</title></head>\n");
        buffer.append("<body>\n");
        buffer.append("<h1>"+title+"</h1>\n");
        buffer.append("<table width='80%' border='3'>\n");
        Iterator it = content.iterator();
        while (it.hasNext()){
            Item item= (Item) it.next();
            buffer.append("<tr>"+item.makeHtml()+"</tr>");
        }
        buffer.append("</table>\n");
        buffer.append("<hr><address>"+author+"</address>");
        buffer.append("</body></html>\n");
        return buffer.toString();
    }
}
