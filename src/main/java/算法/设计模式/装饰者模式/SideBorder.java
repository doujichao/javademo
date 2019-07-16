package 算法.设计模式.装饰者模式;

/**
 * 侧边边框
 */
public class SideBorder extends Border {

    /**
     * 边框字符
     */
    private char borderChar;

    public SideBorder(Display display,char ch) {
        super(display);
        borderChar=ch;
    }

    @Override
    public int getColumns() {
        return 1+display.getColumns()+1;
    }

    @Override
    public int getRows() {
        return display.getRows();
    }

    @Override
    public String getRowText(int row) {
        return borderChar+display.getRowText(row)+borderChar;
    }
}
