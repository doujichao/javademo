package 算法.设计模式.模板方法模式;

/**
 * 模板方法字符实现类
 */
public class StringDisplay extends AbstractDisplay {
    private String msg;
    private int width;
    public StringDisplay(String msg) {
        this.msg = msg;
        this.width=msg.length();
    }

    @Override
    protected void open() {
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
    protected void print() {
        System.out.println("|"+msg+"|");
    }

    @Override
    protected void close() {
        printLine();
    }


}
