package 算法.设计模式.抽象工厂.factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * 产品类
 */
public abstract class Page {

    protected String title;

    protected String author;

    protected ArrayList content=new ArrayList();

    public Page(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void add(Item item){
        content.add(item);
    }

    public void output(){
        try {
            String filename=title+".html";
            Writer writer=new FileWriter(filename);
            writer.write(this.makeHtml());
            writer.close();
            System.out.println(filename+"编写完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String makeHtml();
}
