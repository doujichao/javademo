package spring.aop.pointcut.simple;

import spring.aop.before.Singer;

public class GreatGuitarist implements Singer {
    @Override
    public void sing() {
        System.out.println("I shot the sheriff , \n" +
                "But I did not shoot the deputy");
    }
}
