package java.classloader;

/**
 * 通过子类引用父类的静态变量，不会触发子类的初始化
 */
public class NotInitialization {
    public static void main(String[] args){
//        System.out.println(SubClass.value);
//        SuperClass[] sca=new SuperClass[10];
        System.out.println(SuperClass.HELLO);
        System.out.println(null+"");
    }
}

class SuperClass{
    static {
        System.out.println("SuperClass init");
    }
    public static int value=123;
    public static final String HELLO="hello world";
}

class SubClass extends SuperClass{
    static {
        System.out.println("SubClass init");
    }
}
