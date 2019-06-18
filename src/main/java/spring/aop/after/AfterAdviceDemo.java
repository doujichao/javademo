package spring.aop.after;

import org.springframework.aop.framework.ProxyFactory;

public class AfterAdviceDemo {


    public static void main(String[] args) {
        KeyGenerator keyGenerator = getKeyGenerator();
        for (int x = 0; x < 10; x++) {
            try {
                long key = keyGenerator.getKey();
                System.out.println("Key:" + key);
            } catch (SecurityException ex) {
                System.out.println("Weak Key Generated!");
            }
        }
    }

    private static KeyGenerator getKeyGenerator() {
        KeyGenerator target = new KeyGenerator();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvice(new WeakKeyCheckAdvice());
        return (KeyGenerator) pf.getProxy();
    }

}
