package 算法.设计模式.仲裁者模式;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 单选按钮
 */
public class ColleagueCheckbox  extends Checkbox implements ItemListener,Colleague {

    private Mediator mediator;

    public ColleagueCheckbox(String label, CheckboxGroup group,boolean state) throws HeadlessException {
        super(label, state, group);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        mediator.colleagueChanged();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
    }

    @Override
    public void action(boolean enabled) {
        setEnabled(enabled);
    }
}
