package spring.aop.cglib;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ProxyPerTest {
    public static void main(String[] args) {
        SimpleBean target = new DefaultSimpleBean();
        Advisor advisor = new DefaultPointcutAdvisor(
                new PointCutDemo(), new BeforeMethod());
        runCglibTest(advisor, target);
        runCglibFrozenTests(advisor, target);
        runJdkTests(advisor, target);
    }

    private static void runJdkTests(Advisor advisor, SimpleBean target) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setInterfaces(SimpleBean.class);

        SimpleBean proxy = (SimpleBean) proxyFactory.getProxy();
        System.out.println("Runing jdk tests");
        test(proxy);
    }

    private static void runCglibFrozenTests(Advisor advisor, SimpleBean target) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setFrozen(true);

        SimpleBean proxy = (SimpleBean) proxyFactory.getProxy();
        System.out.println("Runing Cglib standard tests");
        test(proxy);
    }

    private static void runCglibTest(Advisor advisor, SimpleBean target) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);

        SimpleBean proxy = (SimpleBean) proxyFactory.getProxy();
        System.out.println("Runing Cglib standard tests");
        test(proxy);
    }

    private static void test(SimpleBean bean) {
        long before = 0;
        long after = 0;
        System.out.println("Testing Advised Method");
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.advised();
        }
        after = System.currentTimeMillis();
        System.out.println("Took " + (after - before) + " ms\n");

        System.out.println("Testing Unadvised Method");
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.unadvised();
        }
        after = System.currentTimeMillis();
        System.out.println("Took " + (after - before) + " ms\n");

        System.out.println("Testing equals() Method");
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.equals(bean);
        }
        after = System.currentTimeMillis();
        System.out.println("Took " + (after - before) + " ms\n");

        System.out.println("Testing hashCode() Method");
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            bean.hashCode();
        }
        after = System.currentTimeMillis();
        System.out.println("Took " + (after - before) + " ms\n");

        Advised advised = (Advised) bean;
        System.out.println("Testing Advised.getProxyTargetClass() Method");
        before = System.currentTimeMillis();
        for (int x = 0; x < 500000; x++) {
            advised.getTargetClass();
        }
        after = System.currentTimeMillis();
        System.out.println("Took " + (after - before) + " ms\n");

        System.out.println(">>>\n");
    }
}
