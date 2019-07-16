package 算法.设计模式;

import org.junit.Test;
import 算法.设计模式.抽象工厂.factory.Factory;
import 算法.设计模式.抽象工厂.factory.Link;
import 算法.设计模式.抽象工厂.factory.Page;
import 算法.设计模式.抽象工厂.factory.Tray;
import 算法.设计模式.抽象工厂.table.TableFactory;
import 算法.设计模式.桥梁模式.CountDisplay;
import 算法.设计模式.桥梁模式.Display;
import 算法.设计模式.桥梁模式.StringDisplayImpl;
import 算法.设计模式.策略模式.Hand;
import 算法.设计模式.策略模式.Player;
import 算法.设计模式.策略模式.ProbStrategy;
import 算法.设计模式.策略模式.WinningStrategy;
import 算法.设计模式.组合模式.Directory;
import 算法.设计模式.组合模式.File;

public class Main1 {

    @Test
    public void testAbstractFactory(){
//        Factory factory = Factory.getFactory(ListFactory.class.getName());
        Factory factory = Factory.getFactory(TableFactory.class.getName());
        Link people = factory.createLink("人民日报", "http://www.people.com.cn/");
        Link gmw = factory.createLink("光明日报", "http://www.gmw.cn/");

        Link us_yahoo = factory.createLink("Yahoo!", "http://www.yahoo.com/");
        Link jp_yahoo = factory.createLink("Yahoo!Japan", "http://www.yahoo.co.jp");
        Link excite = factory.createLink("Excite", "http://www.excite.com/");
        Link google = factory.createLink("Google", "http://www.google.com/");

        Tray traynews = factory.createTray("日报");
        traynews.add(people);
        traynews.add(gmw);

        Tray trayyahoo = factory.createTray("Yahoo!");
        trayyahoo.add(us_yahoo);
        trayyahoo.add(jp_yahoo);

        Tray traysearch = factory.createTray("检索引擎");
        traysearch.add(trayyahoo);
        traysearch.add(excite);
        traysearch.add(google);

        Page page = factory.createPage("LinkPage", "ddd");
        page.add(traynews);
        page.add(traysearch);
        page.output();
    }

    @Test
    public void testBridge(){
        Display d1=new Display(new StringDisplayImpl("Hello,china"));
        Display d2=new Display(new StringDisplayImpl("Hello,china"));
        CountDisplay d3=new CountDisplay(new StringDisplayImpl("Hello,china"));
        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
    }

    @Test
    public void testStrategy(){
        Player player1=new Player("Taro",new WinningStrategy(100));
        Player player2=new Player("Hana",new ProbStrategy(200));
        for (int i=0;i<10000;i++){
            Hand nextHand1 = player1.nextHand();
            Hand nextHand2 = player2.nextHand();
            if (nextHand1.isStrongerThan(nextHand2)){
                System.out.println("Winner:"+player1);
                player1.win();
                player2.lose();
            }else if (nextHand2.isStrongerThan(nextHand1)){
                System.out.println("Winner:"+player2);
                player2.win();
                player1.lose();
            }else {
                System.out.println("Even....");
                player1.even();
                player2.even();
            }
        }

        System.out.println("Total result:");
        System.out.println(player1.toString());
        System.out.println(player2.toString());
    }


    @Test
    public void testComposite(){
        System.out.println("Making root entires");
        Directory rootdir=new Directory("root");
        Directory bindir=new Directory("bin");
        Directory tempdir=new Directory("temp");
        Directory userdir=new Directory("usr");
        rootdir.add(bindir);
        rootdir.add(tempdir);
        rootdir.add(userdir);
        bindir.add(new File("vi",10000));
        bindir.add(new File("latex",20000));
        rootdir.printList();
        System.out.println();
        System.out.println("Making user entries...");
        Directory yuki=new Directory("yuki");
        Directory hanako=new Directory("hanako");
        Directory tomura=new Directory("tomura");
        userdir.add(yuki);
        userdir.add(hanako);
        userdir.add(tomura);
        yuki.add(new File("diary.html",100));
        yuki.add(new File("Composite.java",300));
        hanako.add(new File("memo.txt",300));
        tomura.add(new File("game.doc",400));
        tomura.add(new File("junk.mail",500));
        rootdir.printList();
    }

}
