package spring.aop.cglib;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * 过滤advise方法
 */
public class PointCutDemo extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return ("advise".equals(method.getName()));
    }

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> clazz == DefaultSimpleBean.class;
    }
}
