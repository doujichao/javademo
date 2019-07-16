package util.reflect;

public class Shi {
    static {
        System.out.println("static初始化一坨");
    }

    public static String staticString=getString();

    private static String getString() {
        System.out.println("静态函数Field初始化一坨");
        return "静态函数Field初始化一坨";
    }

    public Shi(){
        System.out.println("构造函数初始化一坨");
    }


    public  String shi="一坨屎";

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }
}
