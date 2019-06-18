package spring.aop.pointcut.dynamic;

public class SampleBean {
    public void foo(int x){
        System.err.println("Invoded foo() with: "+x);
    }

    public void bar(){
        System.err.println("Invoked bar()");
    }
}
