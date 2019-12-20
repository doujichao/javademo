package 集合;


import org.junit.Test;

import java.util.*;

public class Demo {
    /**
     * 测试填充数组和集合
     */
    @Test
    public void testFill(){
        //数组的填充
        int len=5;
        int[] i=new int[len];
        Arrays.fill(i,99);
        System.out.println(Arrays.toString(i));
        //集合的填充
        List<String> list=new ArrayList<>(
                Collections.nCopies(12,"hello")
        );
        Collections.fill(list,"你好");
        System.out.println(list);
    }

}
