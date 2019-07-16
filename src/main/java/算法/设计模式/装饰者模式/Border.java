package 算法.设计模式.装饰者模式;

/**
 * 边框扩展类
 */
public abstract class Border extends Display {

    protected Display display;

    protected Border(Display display) {
        this.display = display;
    }
}
