package 算法.设计模式.单例模式;

/**
 * 单例模式
 */
public class Singleton {

    private static Singleton singleton=new Singleton();

    private Singleton(){
        System.out.println("生成一个实例");
    }

    public static Singleton getInstance(){
        return singleton;
    }

}
