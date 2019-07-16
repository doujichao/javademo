package 算法.设计模式.享元模式;

import java.util.HashMap;

public class BigCharFactory {
    //管理已经生成的BigChar的实例
    private HashMap pool=new HashMap();
    //Singleton模式
    private static BigCharFactory singleton=new BigCharFactory();

    private BigCharFactory(){}

    public static BigCharFactory getInstance() {
        return singleton;
    }

    public synchronized BigChar getBigChar(char charname){
        BigChar bc= (BigChar) pool.get(""+charname);
        if (bc==null){
            bc=new BigChar(charname);
            pool.put(""+charname,bc);
        }
        return bc;
    }
}
