package java.reflect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class Demo {

    @Test
    public void testConstruct() throws Exception {
        Class<Dog> clazz=Dog.class;
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println(constructors[0]);
        System.out.println(constructors[1]);

        Constructor<Dog> constructor = clazz.getConstructor(String.class, String.class);
        Dog dog = constructor.newInstance("小狗", "129");
        System.out.println(dog);
    }

    @Test
    public void testFile() throws Exception {
        Dog dog=new Dog();
        Class<Dog> clazz=Dog.class;

        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
//        Field name = clazz.getField("name");
        name.set(dog,"mak");
        System.out.println(dog);
    }

    @Test
    public void testMethod() throws Exception{
        Dog dog=new Dog();
        Class<Dog> clazz=Dog.class;
        Method[] methods =
                clazz.getDeclaredMethods();
        for(Method m:methods){
            m.setAccessible(true);
            String name = m.getName();
            if(name=="setName")
                m.invoke(dog,"jack");
            else if(name=="setAge")
                m.invoke(dog,"111");
        }
        System.out.println(dog);
    }

    @Test
    public void testModifier() throws Exception{
        Class<Dog> clazz=Dog.class;
        int cla = clazz.getModifiers();
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        int modifiers = name.getModifiers();
        System.out.println(cla== Modifier.PUBLIC);
        System.out.println(modifiers==Modifier.PRIVATE);

    }

    @Test
    public void testPacket() throws Exception{
        Class clazz= Demo.class;
        ClassLoader loader = clazz.getClassLoader();
        //根据统一资源定位符找到具体路径
        URL url = loader.getResource("算法");

        String dir = url.getPath();
        System.out.println(dir);

        File file=new File(url.toURI());
        File[] files = file.listFiles();
        String className= "算法.";
        getClazz(files, className);
    }

    private void getClazz(File[] files, String className) throws ClassNotFoundException {
        Class clazz;
        int len=files.length;
        File f;
        for (int i=0;i<len;i++){
            f=files[i];
            if(!f.isDirectory()){
                String name = f.getName();
                clazz=Class.forName(className+name.substring(0,name.lastIndexOf(".")));
                System.out.println(clazz);
            }else{
                File[] files1 = f.listFiles();
                getClazz(files1,className+f.getName()+".");
                continue;
            }
        }
    }


    @Test
    public void test(){
        Runtime runtime=Runtime.getRuntime();
        System.out.println(runtime);
    }

    @Test
    public void testPropertyCopy() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Dog dog=new Dog("小花","11111");
        Dog dog1=new Dog();
        Class<? extends Dog> aClass = dog.getClass();
        BeanInfo info = Introspector.getBeanInfo(aClass);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor pd:props){
            Method get = pd.getReadMethod();
            Method set = pd.getWriteMethod();
            if(get!=null&&set!=null){
                set.invoke(dog1,get.invoke(dog, null));
            }
        }
        System.out.println(dog1);
    }

    /**
     * 测试内省，专业操纵javaBean
     */
    @Test
    public void testIntrospect() throws IntrospectionException {
        //得到bean的信息
        BeanInfo info = Introspector.getBeanInfo(Dog.class);
        //得到属性描述符
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for(PropertyDescriptor pd:props){
            System.out.println(pd.getName());
            //getXxx
            Method getters = pd.getReadMethod();
            System.out.println(getters==null?null:getters.getName());
            //setXxx
            Method setters = pd.getWriteMethod();
            System.out.println(setters==null?null:setters.getName());
        }
    }

    @Test
    public void testAnnotation(){
        Class clazz=Demo.class;
        Annotation[] annotations =
                clazz.getAnnotations();
        System.out.println(annotations.length);
    }

    /**
     * sun.misc.Launcher$AppClassLoader
     * 类路径:classpath:
     */
    @Test
    public void testClassLoader(){
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL url = loader.getResource("file.properties");
        System.out.println(url);
    }

    /**
     * 测试代理
     */
    @Test
    public void testProxy(){
        DagDao dao=new Dog();

        DogDaoInvacationHandler handler=new DogDaoInvacationHandler(dao);

        DagDao o = (DagDao)Proxy.newProxyInstance(Dog.class.getClassLoader(), Dog.class.getInterfaces(),handler );
        o.insert(new Shi());
    }
}
