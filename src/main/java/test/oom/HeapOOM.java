package test.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {

    static class OOMObject{}

    /**
     * 测试对内存溢出
     */
    @Test
    public void test(){
        List<OOMObject> list=new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }

    /**
     * 测试栈内存溢出
     */
    @Test
    public void testJavaVmStackSOF(){
        HeapOOM heapOOM=new HeapOOM();
        try {
            heapOOM.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:"+heapOOM.stackLength);
            System.out.println(e.getClass());
        }
    }

    private int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    private void dontStop(){
        while (true){}
    }
    public void stackLeadByThread(){
        while (true){
            Thread thread=new Thread(() -> dontStop());
            thread.start();
        }
    }
    /**
     * 创建线程导致内存溢出
     */
    @Test
    public void testJavaOOMByThread(){
        HeapOOM heapOOM=new HeapOOM();
        heapOOM.testJavaOOMByThread();
    }

    /**
     * 运行时常量池溢出
     */
    @Test
    public void testRuntimeConstant(){
        List<String> list=new ArrayList<>();
        int i=0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
