package 网络编程和IO.socket.聊天室;

import 网络编程和IO.socket.聊天室.message.Message;
import 网络编程和IO.socket.聊天室.message.QQUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.*;

public class MyWindow extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5124743494229327624L;
	/**
	 * 全部信息框
	 */
	public JTextArea allmsgText;
	/**
	 * 发送信息框
	 */
	public JTextArea sendText;
	public JTextArea getAllmsgText() {
		return allmsgText;
	}
	public JTextArea getSendText() {
		return sendText;
	}
	public JButton getSendBtn() {
		return sendBtn;
	}
	public JTextArea getFrientList() {
		return frientList;
	}

	/**
	 * 发送按钮
	 */
	private JButton sendBtn;
	/**
	 * 好友列表
	 */
	private JTextArea frientList;

	public MyWindow() {

		this.setBounds(100, 100, 900, 900);

		getContentPane().setLayout(null);

		//全部信息
		allmsgText = new JTextArea();
		allmsgText.setBounds(32, 10, 500, 500);
		getContentPane().add(allmsgText);

		sendText = new JTextArea();
		sendText.setBounds(32, 550, 352, 200);
		getContentPane().add(sendText);

		sendBtn = new JButton("发送");
		sendBtn.setBounds(459, 595, 118, 81);
		getContentPane().add(sendBtn);
		sendBtn.addActionListener(this);	

		frientList = new JTextArea();
		frientList.setBounds(617, 10, 200, 740);
		frientList.setBackground(Color.white);
		frientList.setEditable(false);
		getContentPane().add(frientList);

		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
	}

	private OutputStream clientOs;


	public void setClientOs(OutputStream clientOs) {
		this.clientOs = clientOs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==sendBtn&&"客户端".equals(this.getTitle())) {
			try {
				String text = sendText.getText();
				//发送信息到服务器端
				QQUtil.sendMessage(text,new Message(),clientOs);
				System.out.println("发送信息到服务端："+text);
				sendText.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
