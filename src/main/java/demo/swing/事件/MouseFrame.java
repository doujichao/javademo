package demo.swing.事件;

import javax.swing.*;

public class MouseFrame  extends JFrame {

    public MouseFrame(){
        add(new MouseComponent());
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        new MouseFrame();
    }
}
