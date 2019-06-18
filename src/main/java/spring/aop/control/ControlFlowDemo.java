package spring.aop.control;

import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 *  在指定类，指定方法中调用方法，会调用切面
 */
public class ControlFlowDemo {

    public static void main(String[] args){
        ControlFlowDemo ex=new ControlFlowDemo();
        ex.run();
    }

    private void run() {
        TestBean target=new TestBean();
        Pointcut pc=new ControlFlowPointcut(ControlFlowDemo.class,"test");
        Advisor advisor=new DefaultPointcutAdvisor(pc,new SimpleBeforeAdvice());

        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);

        TestBean proxy= (TestBean) proxyFactory.getProxy();
        System.out.println("\nTrying normal invoke");
        proxy.foo();
        System.out.println("\n\tTrying under ControlFlowDemo.test()");
        test(proxy);
    }

    private void test(TestBean proxy) {
        System.out.println("test invoke ----------------");
        proxy.foo();
    }

}
