package 算法.设计模式;

import org.junit.Test;
import 算法.设计模式.代理模式.Printable;
import 算法.设计模式.代理模式.PrintereProxy;
import 算法.设计模式.仲裁者模式.LoginFrame;
import 算法.设计模式.单例模式.Singleton;
import 算法.设计模式.原型模式.Manager;
import 算法.设计模式.原型模式.MessageBox;
import 算法.设计模式.原型模式.UnderlinePen;
import 算法.设计模式.备忘录模式.Gamer;
import 算法.设计模式.备忘录模式.Memento;
import 算法.设计模式.工厂方法模式.Factory;
import 算法.设计模式.工厂方法模式.IDCardFactory;
import 算法.设计模式.工厂方法模式.Product;
import 算法.设计模式.建造者模式.Director;
import 算法.设计模式.建造者模式.HtmlBuilder;
import 算法.设计模式.建造者模式.TextBuilder;
import 算法.设计模式.模板方法模式.AbstractDisplay;
import 算法.设计模式.模板方法模式.CharDisplay;
import 算法.设计模式.模板方法模式.StringDisplay;
import 算法.设计模式.状态模式.SafeFrame;
import 算法.设计模式.窗口模式.PageMake;
import 算法.设计模式.观察者模式.*;
import 算法.设计模式.解释器模式.Context;
import 算法.设计模式.解释器模式.Node;
import 算法.设计模式.解释器模式.ProgramNode;
import 算法.设计模式.访问者模式.Directory;
import 算法.设计模式.访问者模式.File;
import 算法.设计模式.访问者模式.LIstVisitor;
import 算法.设计模式.责任链模式.*;
import 算法.设计模式.迭代器模式.Book;
import 算法.设计模式.迭代器模式.BookShelf;
import 算法.设计模式.迭代器模式.Iterator;
import 算法.设计模式.适配器模式.Print;
import 算法.设计模式.适配器模式.Print1;
import 算法.设计模式.适配器模式.PrintBanner;
import 算法.设计模式.适配器模式.PrintBanner1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    @Test
    public void testIterator(){
        BookShelf bookShelf=new BookShelf(4);
        bookShelf.appendBook(new Book("Around the World in 80 Days"));
        bookShelf.appendBook(new Book("Bible"));
        bookShelf.appendBook(new Book("Cinderella"));
        bookShelf.appendBook(new Book("Daddy-Long-Legs"));
        Iterator iterator = bookShelf.iterator();
        while (iterator.hasNext()){
            Book next = (Book) iterator.next();
            System.out.println(next.getName());
        }
    }

    @Test
    public void testAdapter(){
        //通过继承实现的适配器模式
        Print p=new PrintBanner("String");
        p.printStrong();
        p.printWeak();

        //通过委托来实现适配器模式
        Print1 p1=new PrintBanner1("String");
        p1.printStrong();
        p1.printWeak();
    }

    @Test
    public void testTemplateMethod(){
        AbstractDisplay d1=new CharDisplay('H');
        AbstractDisplay d2=new StringDisplay("Hello world.");
        AbstractDisplay d3=new StringDisplay("你好 世界");
        d1.display();
        d2.display();
        d3.display();
    }

    @Test
    public void testFactoryMethod(){
        Factory factory=new IDCardFactory();
        Product card1 = factory.create("小明");
        Product card2 = factory.create("小红");
        Product card3 = factory.create("小杠");
        card1.use();
        card2.use();
        card3.use();
    }

    @Test
    public void testSingleton(){
        Singleton s11 = Singleton.getInstance();
        Singleton s22 = Singleton.getInstance();
        System.out.println(s11==s22);
    }

    @Test
    public void testPrototype(){
        //准备
        Manager manager=new Manager();
        UnderlinePen upen=new UnderlinePen('~');
        MessageBox mbox=new MessageBox('*');
        MessageBox sbox=new MessageBox('/');
        manager.register("strong message",upen);
        manager.register("warning box",mbox);
        manager.register("slash box",sbox);
        
        //生成
        算法.设计模式.原型模式.Product p1 = manager.create("strong message");
        p1.use("Hello, wold");
        System.out.println(p1);
        System.out.println(upen);
        算法.设计模式.原型模式.Product p2 = manager.create("warning box");
        p2.use("Hello,World.");
        算法.设计模式.原型模式.Product p3 = manager.create("slash box");
        p3.use("Hello world.");
    }

    @Test
    public void testBuilderText(){
        TextBuilder textBuilder=new TextBuilder();
        Director director=new Director(textBuilder);
        director.construct();
        String result = textBuilder.getResult();
        System.out.println(result);
    }

    @Test
    public void testBuilderHtml(){
        HtmlBuilder textBuilder=new HtmlBuilder();
        Director director=new Director(textBuilder);
        director.construct();
        String result = textBuilder.getResult();
        System.out.println(result);
    }

    @Test
    public void testVisitor(){
        System.out.println("Making root entries...");
        Directory rootdir=new Directory("root");
        Directory binddir=new Directory("bin");
        Directory tmpdir=new Directory("tmp");
        Directory usrdir=new Directory("usr");
        rootdir.add(binddir);
        rootdir.add(tmpdir);
        rootdir.add(usrdir);
        binddir.add(new File("vi",10000));
        binddir.add(new File("latex",20000));
        rootdir.accept(new LIstVisitor());
    }

    @Test
    public void testChainOfResponsibility(){
        Support alice=new NoSupport("Alice");
        Support bob=new LimitSupport("bob",100);
        Support charlie=new SpecialSupport("Charlie",429);
        Support diana=new LimitSupport("Diano",200);
        Support elmo=new OddSupport("Elmo");
        Support fred=new LimitSupport("Fred",300);
        alice.setNext(bob).setNext(charlie).setNext(diana).setNext(elmo).setNext(fred);
        for (int i=0;i<500;i+=33){
            alice.support(new Trouble(i));
        }
    }

    @Test
    public void testFacade(){
        PageMake.makeWelcomPage("hyuki@hyuki.com","main.html");
    }

    @Test
    public void testMediator(){
        new LoginFrame("Mediator Sample");
    }

    @Test
    public void testObserver(){
        NumberGenerator generator=new RandomNumberGenerator();
        Observer observer1=new DigitObserver();
        Observer observer2=new GraphObserver();
        generator.addObserver(observer1);
        generator.addObserver(observer2);
        generator.execute();
    }

    @Test
    public void testMemento(){
        Gamer gamer=new Gamer(100);
        Memento memento = gamer.createMemento();
        for (int i=0;i<100;i++){
            System.out.println("======="+i);
            System.out.println("当前状态："+gamer);

            //进行游戏
            gamer.bet();

            System.out.println("所持金钱为"+gamer.getMoney()+"元。");
            if (gamer.getMoney()>memento.getMoney()){
                System.out.println("    所持金钱增加了保存当前状态    ");
                memento=gamer.createMemento();
            }else if (gamer.getMoney()<memento.getMoney()/2){
                System.out.println("    所持金钱减少了，恢复以前状态     ");
                gamer.restoreMemento(memento);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    @Test
    public void testState(){
        SafeFrame frame=new SafeFrame("State Sample");
        while (true){
            for (int hour=0;hour<24;hour++){
                frame.setClock(hour);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testProxy(){
        Printable p=new PrintereProxy("Alice");
        System.out.println("现在的名字是"+p.getPrinterName()+"。");
        p.setPrinterName("Bob");
        System.out.println("现在的名字是"+p.getPrinterName()+"。");
        p.print("Hello world.");
    }

    @Test
    public void testInterperter(){
        try {
            BufferedReader reader=new BufferedReader(new FileReader("src/main/java" +
                    "/算法/设计模式/解释器模式/program.txt"));
            String text;
            while ((text=reader.readLine())!=null){
                System.out.println("text = \""+text+"\"");
                Node node=new ProgramNode();
                node.parse(new Context(text));
                System.out.println("node = "+node);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
