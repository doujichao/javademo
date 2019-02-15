package swing;

import javax.swing.*;
import java.awt.*;

public class HTMLDemo  extends JFrame {

    private JLabel jLabel;
    private JButton jButton;

    public HTMLDemo (String title){
        super(title);

        jLabel=new JLabel("<html><b><i>Hello World!</i></b></html>");

        //假定go.jpg文件与HTMLDemo.class文件位于同一目录下
        jButton=new JButton("<html><img src=\""+this.getClass().getResource("/go.jpg")+"\"\\></html>");
        //设置鼠标移动到该Button时的提示信息
        jButton.setToolTipText("开始");

        Container container=getContentPane();
        container.setLayout(new GridLayout(2,1));
        container.add(jLabel);
        container.add(jButton);

        //调整窗口大小使其能够适应布局
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
