package 算法.设计模式.桥梁模式;

/**
 * 只显示规定的次数，类的功能层次结构
 */
public class CountDisplay extends Display{
    public CountDisplay(DisplayImpl impl) {
        super(impl);
    }

    public void multiDisplay(int times){
        open();
        for (int i=0;i<times;i++){
            print();
        }
        close();
    }
}
