package 算法.设计模式.仲裁者模式;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 登陆框架
 */
public class LoginFrame extends JFrame implements ActionListener,Mediator {
    private ColleagueCheckbox checkGuest;
    private ColleagueCheckbox checkLogin;
    private ColleagueTextField textUser;
    private ColleagueTextField textPass;
    private ColleagueButton buttonOk;
    private ColleagueButton buttonCancel;

    public LoginFrame(String title) throws HeadlessException {
        super(title);
        setBackground(Color.lightGray);
        //使用网格布局
        setLayout(new GridLayout(4,2));
        //生成各个Colleagues
        createColleagues();
        //配置
        add(checkGuest);
        add(checkLogin);
        add(new Label("Username:"));
        add(textUser);
        add(new Label("Password:"));
        add(textPass);
        add(buttonOk);
        add(buttonCancel);
        //设置初始的启动/禁止状态
        colleagueChanged();
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(0);
    }

    @Override
    public void createColleagues() {
        CheckboxGroup g=new CheckboxGroup();
        checkGuest=new ColleagueCheckbox("Guest",g,true);
        checkLogin=new ColleagueCheckbox("Login",g,false);
        textUser=new ColleagueTextField("",10);
        textPass=new ColleagueTextField("",10);
        textPass.setEchoChar('*');
        buttonOk=new ColleagueButton("OK");
        buttonCancel=new ColleagueButton("Cancel");

        //设置Mediator仲裁者
        checkGuest.setMediator(this);
        checkLogin.setMediator(this);
        textPass.setMediator(this);
        textUser.setMediator(this);
        buttonCancel.setMediator(this);
        buttonOk.setMediator(this);

        //设置Listener
        checkGuest.addItemListener(checkGuest);
        checkLogin.addItemListener(checkLogin);
        textUser.addTextListener(textUser);
        textPass.addTextListener(textPass);
        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);
    }

    /**
     * 接受来自于Colleague的通知然后判断各个Colleage的启动/禁用状态
     */
    @Override
    public void colleagueChanged() {
        //如果使访客模式
        if (checkGuest.getState()){
            textUser.action(false);
            textPass.action(false);
            buttonOk.action(true);
        }else {
            textUser.action(true);
            userpassChanged();
        }
    }

    private void userpassChanged() {
        //如果用户有输入
        if (textUser.getText().length()>0){
            textPass.action(true);
            if (textPass.getText().length()>0){
                buttonOk.action(true);
            }else {
                buttonOk.action(false);
            }
        }else {
            textPass.action(false);
            buttonOk.action(false);
        }
    }
}
