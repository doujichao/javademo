package classloader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 */
public class JavaClassExecuter {

    /**
     * 执行外部传过来的代表一个java类的Byte数组
     * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info劫持后改为HackSystem类
     * 执行方法为该类的static main(String[] args),输出结果为向System.out/err输出信息
     * @param classByte
     * @return
     */
    public static String execute(byte[] classByte){
        HackSystem.clearBuff();
        ClassModifier cm=new ClassModifier(classByte);
        byte[] modifyBytes = cm.modifyUTF8Constant("java/lang/System", "classloader/HackSystem");
        HotSwapClassLoader loader=new HotSwapClassLoader();
        Class clazz = loader.loadByte(modifyBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null,new String[]{null});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }

}
