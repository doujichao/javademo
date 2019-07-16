package 算法.设计模式.窗口模式;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * 使用database类和htmlwriter来生成指定用户的web页面
 */
public class PageMake {
    private PageMake(){}

    public static void makeWelcomPage(String mailaddr,String filename){
        try {
            Properties mailprop = Database.getProperties("maildata");
            String username = mailprop.getProperty(mailaddr);
            HtmlWriter writer=new HtmlWriter(new FileWriter(filename));
            writer.title("Welcome to "+username+"'s page!");
            writer.paragraph(username+"欢迎来到"+username+"的主页");
            writer.paragraph("等着你的邮件");
            writer.mailto(mailaddr,username);
            writer.close();
            System.out.println(filename+" is created for "+mailaddr+" (" +username+")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
