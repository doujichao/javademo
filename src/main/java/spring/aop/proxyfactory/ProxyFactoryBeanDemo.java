package spring.aop.proxyfactory;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ProxyFactoryBeanDemo {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("aopApplication.xml");
        ctx.refresh();

        Documentarist documentaristOne = ctx.getBean("documentaristOne", Documentarist.class);
        Documentarist ducomentaristTwo = ctx.getBean("ducomentaristTwo", Documentarist.class);

        System.out.println("Documentarist One >>");
        documentaristOne.execute();

        System.out.println("Docuemntarist Two >>");
        ducomentaristTwo.execute();
    }
}
