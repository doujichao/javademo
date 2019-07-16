package 算法.设计模式.责任链模式;

/**
 * 用于解决问题的抽象类
 */
public abstract class Support {
    private String name;
    private Support next;

    public Support(String name) {
        this.name = name;
    }

    public Support setNext(Support next) {
        this.next = next;
        return next;
    }

    public final void support(Trouble trouble){
        if (resolve(trouble)){
            done(trouble);
        }else if (next!=null){
            next.support(trouble);
        }else {
            fail(trouble);
        }
    }

    @Override
    public String toString() {
        return "["+name+"]";
    }

    protected void fail(Trouble trouble){
        System.out.println(trouble+" cannot be resolved.");
    }

    protected void done(Trouble trouble){
        System.out.println(trouble+" is resolved by "+this+".");
    }

    protected abstract boolean resolve(Trouble trouble);
}
