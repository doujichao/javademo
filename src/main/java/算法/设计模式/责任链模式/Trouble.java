package 算法.设计模式.责任链模式;

/**
 * 指示发生问题的类
 */
public class Trouble {

    private int number;
    public Trouble (int number){
        this.number=number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "[Trouble "+number+" ]";
    }
}
