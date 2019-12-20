package spring.aop.composable;

import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import spring.aop.before.Guitarist;
import spring.aop.control.SimpleBeforeAdvice;

import java.lang.reflect.Method;

public class ComposablePointcutExample {

    public static void main(String[] args) {
        GrammyGuitarist johnMayer = new GrammyGuitarist();
        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE,
                new SingMethodMatcher());
        System.out.println("Demo 1 >>");
        GrammyGuitarist proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);

        System.out.println();

        System.out.println("Demo 2 >>");
        pc.union(new TalkMethodMatcher());
        proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);
        System.out.println();

        System.out.println("Demo 3 >>");
        pc.intersection(new RestMethodMatcher());
        proxy = getProxy(pc, johnMayer);
        testInvoke(proxy);
        System.out.println();
    }

    private static void testInvoke(GrammyGuitarist proxy) {
        proxy.sing();
        proxy.sing(new Guitarist());
        proxy.talk();
        proxy.rest();
    }

    private static GrammyGuitarist getProxy(ComposablePointcut pc, GrammyGuitarist target) {
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleBeforeAdvice());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(target);
        return (GrammyGuitarist) proxyFactory.getProxy();
    }

    private static class SingMethodMatcher extends StaticMethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return (method.getName().startsWith("si"));
        }
    }

    private static class TalkMethodMatcher extends StaticMethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return "talk".equals(method.getName());
        }
    }

    private static class RestMethodMatcher extends StaticMethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getName().endsWith("st");
        }
    }
}
