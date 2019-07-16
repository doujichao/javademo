package util.reflect;

import java.lang.reflect.Array;

public class Test {

    @org.junit.Test
    public void testStatic() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> name = Class.forName("util.reflect.Shi");
        Shi shi = (Shi)name.newInstance();
        System.out.println(shi.shi);
        System.out.println(Shi.staticString);
    }

    /**
     * 测试java.lang.util.reflect.Array反射创建对象
     */
    @org.junit.Test
    public void testArray() throws ClassNotFoundException {
        Class<?> classType = Class.forName("java.lang.String");
        //创建一个长度为10的字符串数组
        Object array = Array.newInstance(classType, 10);
        //把索引位置为5的元素设为“hello”
        Array.set(array,5,"hello");
        //读取索引位置为5的元素的值
        System.out.println(Array.get(array,5));
    }
    /**
     * 测试java.lang.util.reflect.Array反射创建多维数组
     */
    @org.junit.Test
    public void testArrays() {
       int[] dims=new int[]{5,10,15};
        Object array = Array.newInstance(Integer.TYPE, dims);
        //使用arrayObj引用array[3]
        Object arrayObj = Array.get(array, 4);
        Class<?> cls = arrayObj.getClass().getComponentType();
        System.out.println(cls);
        //使用arrayObj引用array[3][5]
        arrayObj=Array.get(arrayObj,5);
        //把元素array[3][5][10]设为37
        Array.setInt(arrayObj,10,7);
        int arrayCast[][][]= (int[][][]) array;
        System.out.println(arrayCast[4][5][10]);
    }
}
