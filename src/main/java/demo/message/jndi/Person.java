package demo.message.jndi;

import java.io.Serializable;

public class Person implements Serializable {

    private String name = "";
    private String age = "";

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
