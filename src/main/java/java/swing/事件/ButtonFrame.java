package java.swing.事件;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonFrame extends JFrame
{
    private JPanel buttonPanel;
    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;

    public ButtonFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        JButton yellowButton=new JButton("Yellow");
        JButton blueButton=new JButton("Blue");
        JButton redButton=new JButton("Red");

        buttonPanel=new JPanel();

        buttonPanel.add(yellowButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(redButton);

        add(buttonPanel);

        //创建事件
        ColorAction yellowAction=new ColorAction(Color.YELLOW);
        ColorAction blueAction=new ColorAction(Color.BLUE);
        ColorAction redAction=new ColorAction(Color.RED);

        yellowButton.addActionListener(yellowAction);
        blueButton.addActionListener(blueAction);
        redButton.addActionListener(redAction);

        setVisible(true);
    }

    private class ColorAction implements ActionListener{

        private Color backgroudColor;

        public ColorAction(Color c){
            backgroudColor=c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonPanel.setBackground(backgroudColor);
        }
    }

    public static void main(String[] args){
        new ButtonFrame();
    }
}
