package spring;

import org.springframework.context.support.GenericXmlApplicationContext;

public class John {

    private String name;
    private int age;
    private float height;
    private boolean programmer;
    private Long agaInseconds;

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load("application.xml");
        context.refresh();
        Object john = context.getBean("John");
        System.out.println(john);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setProgrammer(boolean programmer) {
        this.programmer = programmer;
    }

    public void setAgaInseconds(Long agaInseconds) {
        this.agaInseconds = agaInseconds;
    }

    @Override
    public String toString() {
        return "John{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", programmer=" + programmer +
                ", agaInseconds=" + agaInseconds +
                '}';
    }
}
