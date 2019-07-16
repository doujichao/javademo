package 算法.设计模式.状态模式;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafeFrame extends JFrame implements ActionListener,Context {

    private JTextField textClock=new JTextField(60);
    private JTextArea testScreen=new JTextArea(10,60);
    private JButton buttonUser=new JButton("使用金库");
    private JButton buttonAlarm=new JButton("按下警铃");
    private JButton buttonPhone=new JButton("正常通话");
    private JButton buttonExit=new JButton("结束");

    private State state=DayState.getInstance();

    public SafeFrame(String title){
        super(title);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        add(textClock,BorderLayout.NORTH);
        textClock.setEditable(false);
        add(testScreen,BorderLayout.CENTER);
        testScreen.setEnabled(false);
        //为界面添加按钮
        Panel panel=new Panel();
        panel.add(buttonUser);
        panel.add(buttonAlarm);
        panel.add(buttonPhone);
        panel.add(buttonExit);
        add(panel,BorderLayout.SOUTH);
        pack();
        setVisible(true);
        buttonUser.addActionListener(this);
        buttonAlarm.addActionListener(this);
        buttonPhone.addActionListener(this);
        buttonExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (e.getSource()==buttonUser){
            state.doUse(this);
        }else if (e.getSource()==buttonAlarm){
            state.doAlarm(this);
        }else if (e.getSource()==buttonPhone){
            state.doPhone(this);
        }else if (e.getSource()==buttonExit){
            System.exit(0);
        }else {
            System.out.println("?");
        }
    }

    @Override
    public void setClock(int hour) {
        StringBuffer clock=new StringBuffer("现在时间是");
        clock.append(hour+":00");
        System.out.println(clock);
        textClock.setText(clock.toString());
        state.doClock(this,hour);
    }

    @Override
    public void changeState(State instance) {
        System.out.println("从"+this.state+"状态变为了"+instance+"状态");
        this.state=instance;
    }

    @Override
    public void recordLog(String s) {
        testScreen.append("Record..."+s+"\n");
    }

    @Override
    public void callSecurityCenter(String s) {
        testScreen.append("Call! "+s+"\n");
    }
}
