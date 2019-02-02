package 网络编程和IO.屏幕广播;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class StudentUI extends JFrame {

    private JLabel label;

    public StudentUI(){
        init();
    }

    private void init() {
        this.setSize(1920,1080);
        this.setLayout(null);

        label = new JLabel();
        label.setBounds(0,0,1920,1080);
        this.add(label);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        this.setVisible(true);
    }

    /**
     * 刷新image
     * @param image
     */
    public void refreshImage(BufferedImage image) {
        label.setIcon(new ImageIcon(image));
        this.repaint();
    }
}
