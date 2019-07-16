package 算法.设计模式.原型模式;

import java.util.HashMap;

/**
 * 使用Product接口来进行复制实例
 */
public class Manager {

    private HashMap showcase=new HashMap();

    public void register(String name,Product product){
        showcase.put(name,product);
    }

    /**
     * 克隆的方法
     * @param productName
     * @return
     */
    public Product create(String productName){
        Product p= (Product) showcase.get(productName);
        return p.createClone();
    }

}
