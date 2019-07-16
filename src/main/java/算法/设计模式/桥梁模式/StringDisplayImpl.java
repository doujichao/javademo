package 算法.设计模式.桥梁模式;

/**
 * 类的实现层次结构
 */
public class StringDisplayImpl extends DisplayImpl {

    private String string;
    private int width;

    public StringDisplayImpl(String string) {
        this.string = string;
        this.width=string.getBytes().length;
    }

    @Override
    public void rawOpen() {
        printLine();
    }

    private void printLine() {
        System.out.print("+");
        for (int i=0;i<width;i++){
            System.out.print("-");
        }
        System.out.println("+");
    }

    @Override
    public void rawPrint() {
        System.out.println("|"+string+"|");
    }

    @Override
    public void rawClose() {
        printLine();
    }
}
