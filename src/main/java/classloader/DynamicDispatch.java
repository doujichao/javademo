package classloader;

/**
 * 方法动态分派演示
 */
public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }

    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }

    static class Women extends Human{
        @Override
        protected void sayHello() {
            System.out.println("women say hello");
        }
    }

    public static void main(String[] args){
        Human man=new Man();
        Human women=new Women();
        man.sayHello();
        women.sayHello();
        man=new Women();
        man.sayHello();
    }
}
