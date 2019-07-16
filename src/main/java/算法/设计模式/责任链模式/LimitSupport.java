package 算法.设计模式.责任链模式;

/**
 * 解决编号小于limit值的问题
 */
public class LimitSupport extends Support {

    private int limit;

    public LimitSupport(String name,int limit) {
        super(name);
        this.limit=limit;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        return trouble.getNumber() < limit;
    }
}
