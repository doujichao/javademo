package spring.aop.pointcut.dynamic;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        //过滤指定的方法名
//        System.out.println("Static chech for "+method.getName());
        return ("foo".equals(method.getName()));
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        System.out.println("Dynamic chech for "+method.getName());
        int x=((Integer)args[0]).intValue();
        return (x!=100);
    }

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> (clazz==SampleBean.class);
    }
}
