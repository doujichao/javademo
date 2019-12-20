package demo.swing.事件;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallComponent extends JPanel {
    private static final int DEFAULT_WIDTH=450;
    private static final int DEFAULT_WEIGHT=350;

    private List<Ball> balls=new ArrayList<>();

    public void  add(Ball b){
        balls.add(b);
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g2= (Graphics2D) graphics;
        for (Ball b:balls){
            g2.fill(b.getShape());
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH,DEFAULT_WEIGHT);
    }
}
