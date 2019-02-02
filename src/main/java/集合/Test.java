package 集合;


import java.util.*;

public class Test {
    /**
     * 测试填充数组和集合
     */
    @org.junit.Test
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


    @org.junit.Test
    public void testSet(){
        Set set=new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return -1;
            }
        });
    }
}
