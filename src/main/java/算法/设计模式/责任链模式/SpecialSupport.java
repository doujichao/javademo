package 算法.设计模式.责任链模式;

public class SpecialSupport extends Support {

    private int number;

    public SpecialSupport(String name,int number) {
        super(name);
        this.number=number;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        return trouble.getNumber() == number;
    }
}
