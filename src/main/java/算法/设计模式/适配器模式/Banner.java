package 算法.设计模式.适配器模式;

/**
 * 广告类
 */
public class Banner {

    /**
     * 广告信息
     */
    private String string;

    public Banner(String string) {
        this.string = string;
    }

    /**
     * 展示待括号
     */
    public void showWithParen(){
        System.out.println("("+string+")");
    }

    /**
     * 展示带*号
     */
    public void showWithAster(){
        System.out.println("*"+string+"*");
    }
}
