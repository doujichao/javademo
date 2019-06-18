package spring.aop.pointcut.regex;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import spring.aop.pointcut.simple.SimpleAdvice;

public class RegexPointcutDemo {

    public static void main(String[] args) {
        Guitarist johnMayer = new Guitarist();
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*sing.*");
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, new SimpleAdvice());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(johnMayer);
        proxyFactory.addAdvisor(advisor);
        Guitarist guitarist = (Guitarist) proxyFactory.getProxy();
        guitarist.rest();
        guitarist.sing();
        guitarist.sing2();
    }
}
