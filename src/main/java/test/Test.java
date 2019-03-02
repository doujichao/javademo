package test;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

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
        System.out.println(s1==s2);
        System.out.println(s1==s3);
        System.out.println(s4==s3);
        System.out.println(s3.equals(s4));
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
        String pattern2="At {1,time,short} On {1,date,long},{0} paid {2,number,currency}.";

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



}

