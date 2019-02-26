package util.classloader;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestClassLoader {

    @Test
    public void testProperty(){
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }

    @Test
    public void testClassLoader(){
        DiskClassLoader classLoader=new DiskClassLoader("d:\\2\\");

        try {
            Class<?> c = classLoader.loadClass("Test");

            if (c!=null){
                Object obj = c.newInstance();
                Method methods = c.getDeclaredMethod("say",null);
                methods.invoke(obj,null);

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
