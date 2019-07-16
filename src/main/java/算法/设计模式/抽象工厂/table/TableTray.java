package 算法.设计模式.抽象工厂.table;

import 算法.设计模式.抽象工厂.factory.Item;
import 算法.设计模式.抽象工厂.factory.Tray;

import java.util.Iterator;

public class TableTray extends Tray {
    public TableTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHtml() {
        StringBuffer buffer=new StringBuffer();
        buffer.append("<td>\n");
        buffer.append("<table width='100%' border='1'><tr>\n");
        buffer.append("<td bgcolor='#cccccc' align='center' colspan='"+tray.size()+"'><b>" +
                caption+"</b></td>");
        buffer.append("</tr>\n");
        buffer.append("<tr>\n");
        Iterator it = tray.iterator();
        while (it.hasNext()){
            Item item= (Item) it.next();
            buffer.append(item.makeHtml());
        }
        buffer.append("</tr></table>\n");
        buffer.append("</td>\n");
        return buffer.toString();
    }
}
