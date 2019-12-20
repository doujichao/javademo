package test;

import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import javax.swing.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Explore {NERE,THERE}

public class Test {

    public  Set<String> analyze(Class<?> enumClass){
        System.out.println("---------Analyzing "+enumClass+"------------");
        System.out.println("Interfaces:");
        for (Type t:enumClass.getGenericInterfaces()){
            System.out.println(t);
        }
        System.out.println("Base:"+enumClass.getSuperclass());
        System.out.println("Method:");
        Set<String> methods=new TreeSet<>();
        for (Method m:enumClass.getMethods()){
            methods.add(m.getName());
        }
        System.out.println(methods);
        System.out.println("-----------------------------");
        return methods;
    }

    @org.junit.Test
    public void testEnum() {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethod = analyze(Enum.class);
        System.out.println("Explore.containsAll(Enum)?"+exploreMethods.containsAll(enumMethod));
        exploreMethods.removeAll(enumMethod);
        System.out.println(exploreMethods);
    }

    @org.junit.Test
    public void testStringUtils(){
        String[] strings = StringUtils.tokenizeToStringArray("hfoiao:fhaodihof(fhah)", ":()", false, false);
        System.out.println(Arrays.toString(strings));
    }

    @org.junit.Test
    public void testString(){
        String s1="hello";
        String s2="hello";
        String s3=new String("hello");
        String s4=new String("hello");
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s4));
        System.out.println(s1==s2);
        System.out.println(s1==s3);
        System.out.println(s4==s3);
        System.out.println(s3.equals(s4));
        System.out.println(s3.equals(s1));
        System.out.println(s1.equals(s2));
    }

    @org.junit.Test
    public void testInteger(){
        int i3=128;
        int i4=128;
        Integer i1=new Integer(128);
        Integer i2=new Integer(128);
        int i5=new Integer(128);
        int i6 =new Integer(128);
        Integer i7=128;
        Integer i8=128;
        System.out.println("i1  "+System.identityHashCode(i1));
        System.out.println("i2  "+System.identityHashCode(i2));
        System.out.println("i3  "+System.identityHashCode(i3));
        System.out.println("i4  "+System.identityHashCode(i4));
        System.out.println("i5  "+System.identityHashCode(i5));
        System.out.println("i6  "+System.identityHashCode(i6));
        System.out.println("i7  "+System.identityHashCode(i7));
        System.out.println("i8  "+System.identityHashCode(i8));
        System.out.println(i1 == i2);
        System.out.println(i1 == i3);
        System.out.println(i3 == i4);
        System.out.println(i1 == i7);
        System.out.println(i7 == i8);
        System.out.println(i1 .equals( i2));
        System.out.println(i1 .equals( i3));
    }

    @org.junit.Test
    public void testBigDecimal(){
        BigDecimal bigDecimal = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal);
    }

    @org.junit.Test
    public void testI18N(){
        //信息格式化串
        String pattern1="{0},你好！你于{1}在工商银行存入{2}员";
        String pattern2="At {1,java.time,short} On {1,date,long},{0} paid {2,number,currency}.";

        //用于动态替换占位符的参数
        Object[] params={"John",new GregorianCalendar().getTime(),1.0E3};

        //使用默认本地化对象格式化信息
        String msg1 = MessageFormat.format(pattern1, params);

        //使用指定的本地化对象格式化信息
        MessageFormat mf=new MessageFormat(pattern2, Locale.US);
        String msg2 = mf.format(params);
        System.out.println(msg1);
        System.out.println(msg2);
    }

    @org.junit.Test
    public void testFormatter(){
        Formatter f=new Formatter(System.out);
        f.format("%-15s %5s %10s \n","Item","Qty","Price");
        f.format("%-15s %5s %10s \n","----","---","----");
        f.format("%-15.15s %5d %10.2f \n","Tom",10,10.7);
        f.format("%-15s %5s %10.2f \n","Item","Qty",19.9);
        f.format("%-15s %5s %10s \n","Item","Qty","Price");
    }

    @org.junit.Test
    public void testRegex(){
        Pattern p=Pattern.compile("");
        Matcher m = p.matcher("aaaa");
        boolean matches = m.matches();
        System.out.println(matches);
    }

    @org.junit.Test
    public void testStringTokenizer(){
        String input="But I'm not dead yet ! I feel happy!";
        StringTokenizer stoke=new StringTokenizer(input);
        while (stoke.hasMoreElements()){
            System.out.println(stoke.nextToken()+" ");
        }
        System.out.println();
        System.out.println(Arrays.toString(input.split(" ")));
    }

    @org.junit.Test
    public void testLambda(){
        String[] planets=new String[]{"Mercury","Venus","Earth","Mars","Jupiter","Saturn"
        ,"Uranus","Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order");
        Arrays.sort(planets);
        System.out.println("Sorted by length:");
        Arrays.sort(planets, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(planets));

        Timer t=new Timer(1000,event -> System.out.println("The java.time is "+new Date()));
        t.start();
    }

}

