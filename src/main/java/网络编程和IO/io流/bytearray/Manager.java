package 网络编程和IO.io流.bytearray;

public class Manager extends Employee{

    private Employee Secritary;

    public Manager(String harry_hacker, int i, int i1, int i2, int i3) {
        super(harry_hacker, i, i1, i2, i3);
    }

    public Employee getSecritary() {
        return Secritary;
    }

    public void setSecritary(Employee secritary) {
        Secritary = secritary;
    }
}
