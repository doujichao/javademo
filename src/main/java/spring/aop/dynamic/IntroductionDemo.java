package spring.aop.dynamic;

import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionDemo {
    public static void main(String[] args) {
        Contant target = new Contant();
        target.setName("Jonh Mayer");

        IntroductionAdvisor advisor = new IsModifiedAdvisor();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setOptimize(true);//优化
        Contant proxy = (Contant) pf.getProxy();
        IsModified proxyInterface = (IsModified) proxy;
        System.out.println("Is Contact?: " + (proxy instanceof Contant));
        System.out.println("Is Ismodified?: " + (proxy instanceof IsModified));
        System.out.println("Has been modified?: " + proxyInterface.isModified());
        proxy.setName("Jonh Mayer");
        System.out.println("Has been modified?: " + proxyInterface.isModified());
        proxy.setName("Eric Clapton");
        System.out.println("Has been modified?: " + proxyInterface.isModified());

    }
}
