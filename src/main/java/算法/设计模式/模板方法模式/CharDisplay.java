package 算法.设计模式.模板方法模式;

/**
 * 模板方法字符实现类
 */
public class CharDisplay extends AbstractDisplay {

    /**
     * 待显示的字符
     */
    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    @Override
    protected void open() {
        System.out.print("<<");
    }

    @Override
    protected void print() {
        System.out.print(ch);
    }

    @Override
    protected void close() {
        System.out.println(">>");
    }
}
