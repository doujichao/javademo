package spring.aop.after;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;
import spring.aop.before.Guitarist;

import java.lang.reflect.Method;

/**
 * 后置返回通知
 */
public class SimpleAfterReturningAdvice implements AfterReturningAdvice {

    public static void main(String[] args){
        //吉他弹奏者
        Guitarist guitarist=new Guitarist();
        ProxyFactory pf=new ProxyFactory();
        pf.addAdvice(new SimpleAfterReturningAdvice());
        pf.setTarget(guitarist);

        Guitarist proxy= (Guitarist) pf.getProxy();
        proxy.sing();
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        System.out.println("After '"+method.getName()+" ' put down guitar.");
    }
}
