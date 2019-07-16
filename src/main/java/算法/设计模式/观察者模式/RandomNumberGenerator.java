package 算法.设计模式.观察者模式;

import java.util.Random;

public class RandomNumberGenerator extends NumberGenerator {

    private Random random=new Random();//随机数生成器

    private int number;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i=0;i<20;i++){
            number=random.nextInt(50);
            notifyObservers();
        }
    }
}
