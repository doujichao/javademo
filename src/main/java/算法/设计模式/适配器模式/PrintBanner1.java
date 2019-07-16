package 算法.设计模式.适配器模式;

/**
 * 通过委托来实现的适配器模式
 */
public class PrintBanner1 extends Print1{

    private Banner banner;

    public PrintBanner1(String ss) {
        this.banner = new Banner(ss);
    }

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
