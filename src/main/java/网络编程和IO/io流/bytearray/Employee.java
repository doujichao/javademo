package 网络编程和IO.io流.bytearray;

import java.io.Serializable;

public class Employee implements Serializable {

    private String name;
    private int saray;
    private int birthday;
    private int workyear;
    private int remaidyear;

    public Employee(String harry_hacker, int i, int i1, int i2, int i3) {
        name=harry_hacker;
        saray=i;
        birthday=i1;
        workyear=i2;
        remaidyear=i3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSaray() {
        return saray;
    }

    public void setSaray(int saray) {
        this.saray = saray;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getWorkyear() {
        return workyear;
    }

    public void setWorkyear(int workyear) {
        this.workyear = workyear;
    }

    public int getRemaidyear() {
        return remaidyear;
    }

    public void setRemaidyear(int remaidyear) {
        this.remaidyear = remaidyear;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", saray=" + saray +
                ", birthday=" + birthday +
                ", workyear=" + workyear +
                ", remaidyear=" + remaidyear +
                '}';
    }
}
