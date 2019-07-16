package 算法.设计模式.抽象工厂.factory;

import java.util.ArrayList;

/**
 * 托盘用于防止
 */
public abstract class Tray extends Item{

    protected ArrayList tray=new ArrayList();

    public Tray(String caption) {
        super(caption);
    }

    public void add(Item item){
        tray.add(item);
    }
}
