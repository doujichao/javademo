package regex;

import org.junit.Test;

import java.util.Scanner;
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

    @Test
    public void testResolvePath(){
        Pattern pattern=Pattern.compile("/([^/][\\w=_?\\.]*)");
        String path="https://weibo.com/?c=spr_web/_sq_firefox/_weibo_t001";
        Matcher matcher = pattern.matcher(path);
        String result="";
        for (int i=1;matcher.find();i++){
            if (i==1){
                System.out.println("网站地址为："+matcher.group(1));
            }
            else {
                result+="/"+matcher.group(1);
            }
        }
        System.out.println("请求地址为："+result);
    }

    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        System.out.println("Enter pattern :");
        String patternString = in.nextLine();

        Pattern pattern = Pattern.compile(patternString);
        while (true){
            System.out.println("Enter string to match:");
            String input = in.nextLine();
            if (input == null || input.equals("")) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()){
                System.out.println("Match");
                int g = matcher.groupCount();
                System.out.println("Group:"+g);
                if (g>0){
                    for (int i=0;i<input.length();i++){
                        for ( int j=1;j<=g;j++){
                            if (i==matcher.start(j) && i==matcher.end(j)){
                                System.out.println("()");
                            }
                        }
                        for ( int j=1;j<=g;j++){
                            if (i==matcher.start(j) && i!=matcher.end(j)){
                                System.out.print("(");
                            }
                        }
                        System.out.print(input.charAt(i));
                        for ( int j=1;j<=g;j++){
                            if (i+1!=matcher.start(j) && i+1!=matcher.end(j)){
                                System.out.print(")");
                            }
                        }
                        System.out.println();
                    }
                }else System.out.println("No match");
            }
        }

    }
}
