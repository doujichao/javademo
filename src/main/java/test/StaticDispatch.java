package test;

public class StaticDispatch {

    static abstract class Human{}

    static class Man extends Human{}

    static class Woman extends Human{}

    public void sayHello(Human guy){
        System.out.println("hello,guy!!");
    }

    public void sayHello(Man guy){
        System.out.println("hello,gentleman!!");
    }
    public void sayHello(Woman guy){
        System.out.println("hello,lady!!");
    }

    /**
     * Human man = new Man();
     * 静态类型 Human 编译器可知
     * 实际类型 Man 运行期可知
     * 虚拟机再进行类型判断的时候时根据静态类型，而不是实际类型进行判断的
     * @param args
     */
    public static void main(String[] args){
        Human man=new Man();
        Human women=new Woman();
        StaticDispatch sr=new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(women);
    }
}
