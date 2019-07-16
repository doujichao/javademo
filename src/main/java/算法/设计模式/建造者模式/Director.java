package 算法.设计模式.建造者模式;

/**
 * 使用Builder类中声明的方法来编写文档
 */
public class Director {

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * 编写文档
     */
    public void construct(){
        builder.makeTitle("Greetine");
        builder.makeString("从早上至下午");
        builder.makeItems(new String[]{
                "早上好",
                "下午好"
        });
        builder.makeString("晚上");
        builder.makeItems(new String[]{
                "晚上好。",
                "晚安。",
                "再见。"
        });
        builder.close();
    }
}
