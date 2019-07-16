package 算法.设计模式.仲裁者模式;

import java.awt.*;

public class ColleagueButton extends Button {
    private Mediator mediator;

    public ColleagueButton(String caption){
        super(caption);
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void action(boolean enabled) {
        setEnabled(enabled);
    }
}
