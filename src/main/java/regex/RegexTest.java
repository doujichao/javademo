package regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void testOr(){
        //这里需要注意 [] 字符组里面的 a | e 三个字符都会匹配
        System.out.println(testRegex("gr[a|e]y","gr|y"));//true
    }

    @Test
    public void testBackReference(){
        System.out.println(testRegex("(the)([1-9]*)\\1\\2","the12the12"));
    }

    @Test
    public void testDollar(){
        System.out.println(testRegex("\\$([0-9]|[1-9]([0-9]?){0,2}(,[0-9]{3})*)(\\.[0-9]{0,3})?","$21,342.898"));
    }

    public boolean testRegex(String regex,String str){
        Pattern p=Pattern.compile(regex);
        Matcher matcher = p.matcher(str);
        boolean matches = matcher.matches();
        int x = matcher.groupCount();
        System.out.println(x);
        for (int i=1;i<=x;i++){
            System.out.println(matcher.group(i));
        }
        return matches;
    }

    @Test
    public void testFind(){
        Pattern p=Pattern.compile("<a>");
        Matcher m = p.matcher("<a>1232</a>");
        System.out.println(m.find());

    }
}
