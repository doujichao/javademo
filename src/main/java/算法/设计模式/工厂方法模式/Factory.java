package 算法.设计模式.工厂方法模式;

/**
 * 定义抽象工厂
 */
public abstract class Factory {

    public final Product create(String owner){
        Product p=createProduct(owner);
        registerProject(p);
        return p;
    }

    protected abstract void registerProject(Product p);

    protected abstract Product createProduct(String owner);

}
