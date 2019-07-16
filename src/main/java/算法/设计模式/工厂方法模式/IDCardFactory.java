package 算法.设计模式.工厂方法模式;

import java.util.ArrayList;
import java.util.List;

/**
 * IDcard的实现工厂
 */
public class IDCardFactory extends Factory {

    private List owners=new ArrayList();

    @Override
    protected void registerProject(Product p) {
        owners.add(((IDCard)p).getOwner());
    }

    public List getOwners() {
        return owners;
    }

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }
}
