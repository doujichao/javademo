package spring.aop.pointcut.annotation;

import spring.aop.before.Singer;

public class Guitarist implements Singer {
    @Override
    public void sing() {
        System.out.println("Dream of ways to throw it all away .");
    }

    @AdviceRequired
    public void sing(Guitarist guitarist){
        System.out.println("play:"+guitarist.play());
    }

    private String play() {
        return this.getClass().getName()+"play";
    }

    public void rest(){
        System.out.println("zzz");
    }
}

