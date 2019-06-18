package spring.aop.composable;

import spring.aop.before.Guitarist;
import spring.aop.before.Singer;

public class GrammyGuitarist implements Singer {
    @Override
    public void sing() {
        System.out.println("Sing : gravity is working against me And gravity " +
                "want to bring me down");
    }

    public void sing(Guitarist guitarist) {
        System.out.println("play:" + guitarist.play());
    }

    public void rest() {
        System.out.println("zzz");
    }

    public void talk() {
        System.out.println("talk");
    }
}

