package 算法.设计模式.装饰者模式;

/**
 * 显示类
 */
public abstract class Display {

    public abstract int getColumns();

    public abstract int getRows();

    public abstract String getRowText(int row);

    //全部显示
    public final void show(){
        for (int i=0;i<getRows();i++){
            System.out.println(getRowText(i));
        }
    }
}
