package 算法.设计模式.观察者模式;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 用于生成数值的抽象类
 */
public abstract class NumberGenerator {
    /**
     * 用于保存Observer们
     */
    private ArrayList observers=new ArrayList();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void deleteObserve(Observer observer){
        observers.remove(observer);
    }

    /**
     * 生成新的数据通知所有的Observer更新
     */
    public void notifyObservers(){
        Iterator it = observers.iterator();
        while (it.hasNext()){
            Observer o= (Observer) it.next();
            o.update(this);
        }
    }

    public abstract int getNumber();
    public abstract void execute();

}
