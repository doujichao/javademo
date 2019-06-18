package spring.aop.pointcut.aspect;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import spring.aop.pointcut.regex.Guitarist;
import spring.aop.pointcut.simple.SimpleAdvice;

public class AspectJexpPointcutDemo {

    public static void main(String[] args) {
        Guitarist johnMayer = new Guitarist();


        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* sing*(..))");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAdvice());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(johnMayer);
        proxyFactory.addAdvisor(advisor);

        Guitarist proxy = (Guitarist) proxyFactory.getProxy();
        proxy.sing2();
        proxy.sing();
        proxy.rest();

    }
}
