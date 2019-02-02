package reflect;

public class Test {

    @org.junit.Test
    public void testStatic() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> name = Class.forName("reflect.Shi");
        Shi shi = (Shi)name.newInstance();
        System.out.println(shi.shi);
        System.out.println(shi.staticString);
    }

}
