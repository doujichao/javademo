package 算法.设计模式;

import org.junit.Test;
import 算法.设计模式.装饰者模式.Display;
import 算法.设计模式.装饰者模式.FullBorder;
import 算法.设计模式.装饰者模式.SideBorder;
import 算法.设计模式.装饰者模式.StringDisplay;

public class Main3 {

    @Test
    public void testDecorator(){
        Display b1=new StringDisplay("Hello world.");
        Display b2=new SideBorder(b1,'#');
        Display b3=new FullBorder(b2);
        b1.show();
        b2.show();
        b3.show();
        Display b4=new SideBorder(new FullBorder(
                new FullBorder(new SideBorder(new FullBorder(new StringDisplay("你好，世界")),'*'))
        ),'*');
        b4.show();
    }

}
