package spring.aop.pointcut.annotation;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import spring.aop.pointcut.simple.SimpleAdvice;

public class AnnotationPointcutDemo {
    public static void main(String[] args){
        Guitarist johnMayer=new Guitarist();

        AnnotationMatchingPointcut pointcut=
                AnnotationMatchingPointcut.
                        forMethodAnnotation(AdviceRequired.class);
        Advisor advisor=new DefaultPointcutAdvisor(pointcut,new SimpleAdvice());
        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(johnMayer);

        Guitarist proxy= (Guitarist) proxyFactory.getProxy();
        proxy.sing();
        proxy.sing(new Guitarist());
        proxy.rest();
    }
}
