package spring.aop.pointcut.dynamic;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import spring.aop.pointcut.simple.SimpleAdvice;

public class DynamicPointcutDemo {
    public static void main(String[] args) {
        SampleBean bean = new SampleBean();
        SimpleDynamicPointcut pointcut = new SimpleDynamicPointcut();
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, new SimpleAdvice());
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(bean);
        proxyFactory.addAdvisor(advisor);
        SampleBean proxy = (SampleBean) proxyFactory.getProxy();
        proxy.bar();
        proxy.foo(12);
    }
}
