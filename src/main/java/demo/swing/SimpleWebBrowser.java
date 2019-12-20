package demo.swing;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SimpleWebBrowser extends JFrame implements HyperlinkListener, ActionListener {

    private JTextField jTextField=new JTextField(40);
    private JEditorPane jEditorPane=new JEditorPane();
    private String initialPage="http://www.baidu.com/index.html";


    public SimpleWebBrowser (String title){
        super(title);
        jTextField.setText(initialPage);
        jTextField.addActionListener(this);
        jEditorPane.setEditable(false);
        jEditorPane.addHyperlinkListener(this);

        //监听editorKit属性被重新设置的事件
        jEditorPane.addPropertyChangeListener("editorKit", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("set editorKit");
                EditorKit kit=jEditorPane.getEditorKit();
                if (kit.getClass()== HTMLEditorKit.class){
                    //设置手工提交表单
                    ((HTMLEditorKit)kit).setAutoFormSubmission(false);
                }
            }
        });

        try {
            jEditorPane.setPage(initialPage);
        } catch (IOException e) {
            showError(initialPage);
        }

        JScrollPane scrollPane=new JScrollPane(jEditorPane);
        Container contentPane = getContentPane();
        contentPane.add(jTextField,BorderLayout.NORTH);
        contentPane.add(scrollPane,BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void showError(String initialPage) {
        jEditorPane.setContentType("text/html");
        jEditorPane.setText("<html>无法打开网页："+initialPage+"。输入的URL不合法，或者网页不存在</html>");
    }

    public static void main(String[] args){
        SimpleWebBrowser browser=new SimpleWebBrowser("Simple Web Browser");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            jEditorPane.setPage(jTextField.getText());
        } catch (IOException e1) {
            showError(jTextField.getText());
        }

    }

    /**
     * 处理用户选择超级连接或者提交表单事件
     * @param e
     */
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        try{
            //处理表单提交事件
            if (e.getClass()== FormSubmitEvent.class){
                FormSubmitEvent formSubmitEvent=(FormSubmitEvent)e;
                URL url = formSubmitEvent.getURL();
                String method = formSubmitEvent.getMethod().toString();
                String data = formSubmitEvent.getData();

                if ("GET".equals(method)){
                    jEditorPane.setPage(url.toString()+"?"+data);
                    jTextField.setText(url.toString()+"?"+data);
                }else if ("POST".equals(method)){
                    URLConnection uc = url.openConnection();
                    OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream());
                    out.write(data);
                    out.close();

                    //接收http响应正文
                    InputStream is = uc.getInputStream();
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int len=-1;
                    while ((len=is.read(buff))!=-1){
                        baos.write(buff,0,len);
                    }
                    is.close();

                    jEditorPane.setText(new String(baos.toByteArray()));
                    jTextField.setText(url.toString());
                }
                System.out.println(formSubmitEvent.getData()+"|"+formSubmitEvent.getMethod()
                +"|"+formSubmitEvent.getURL());
            }else  if (e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
                //处理用户选择超链接事件
                jEditorPane.setPage(e.getURL());
                jTextField.setText(e.getURL().toString());
            }
        } catch (IOException e1) {
            showError(e.getURL().toString());
        }
    }

}
