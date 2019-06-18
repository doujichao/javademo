package spring.aop.before;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * 简单使用前置通知
 */
public class SimpleBeforeAdvice implements MethodBeforeAdvice {

    public static void main(String[] args){
        Guitarist johnMayer=new Guitarist();
        ProxyFactory pf=new ProxyFactory();
        pf.addAdvice(new SimpleBeforeAdvice());
        pf.setTarget(johnMayer);

        //创建代理对象
        Guitarist proxy= (Guitarist) pf.getProxy();
        proxy.sing();
    }

    @Override
    public void before(Method method, Object[] objects, Object o) {
        System.out.println("Before '"+method.getName()+"' ,tune guitar.");
    }
}
