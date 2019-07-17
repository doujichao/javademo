package 算法.设计模式.代理模式;

/**
 * 代理所需要的接口
 */
public interface Printable {

    void setPrinterName(String name);
    String getPrinterName();
    void print(String string);
}
