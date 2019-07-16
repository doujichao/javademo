package 算法.设计模式.享元模式;

public class BigString {
    //"大型字符"的数组
    private BigChar[] bigChars;
    //构造函数
    public BigString(String string){
        bigChars=new BigChar[string.length()];
        BigCharFactory factory = BigCharFactory.getInstance();
        for (int i=0;i<bigChars.length;i++){
            bigChars[i]=factory.getBigChar(string.charAt(i));
        }
    }

    public void print(){
        for (int i=0;i<bigChars.length;i++){
            bigChars[i].print();
        }
    }
}
