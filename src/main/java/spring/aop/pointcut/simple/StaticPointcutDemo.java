package spring.aop.pointcut.simple;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import spring.aop.before.Singer;

public class StaticPointcutDemo {
    public static void main(String[] args){
        GoodGuitarist johnMayer=new GoodGuitarist();
        GreatGuitarist ericClapton=new GreatGuitarist();

        Singer proxyOne;
        Singer proxyTwo;
        //切入点 定义过滤类型，不满足的类型直接过滤掉
        Pointcut pc=new SimpleStaticPointcut();
        //通知，主要是定义切入的类型，前、后、环绕等
        Advice advice=new SimpleAdvice();
        Advisor advisor=new DefaultPointcutAdvisor(pc,advice);
        ProxyFactory pf=new ProxyFactory();
        pf.addAdvisors(advisor);
        pf.setTarget(johnMayer);
        proxyOne= (Singer) pf.getProxy();

        pf=new ProxyFactory();
        pf.addAdvisors(advisor);
        pf.setTarget(ericClapton);
        proxyTwo= (Singer) pf.getProxy();

        proxyOne.sing();
        proxyTwo.sing();

    }
}
