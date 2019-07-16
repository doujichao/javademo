package 算法.设计模式.模板方法模式;

/**
 * 模板方法的父类
 */
public abstract class AbstractDisplay {

    protected abstract void open();
    protected abstract void print();
    protected abstract void close();

    /**
     * 父类中定义的模板
     */
    public final void display(){
        open();
        for (int i=0;i<5;i++){
            print();
        }
        close();
    }
}
