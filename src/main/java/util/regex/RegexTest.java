package util.regex;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
                    for (int i = 0; i < input.length(); i++) {
                        for (int j = 1; j <= g; j++) {
                            if (i==matcher.start(j) && i==matcher.end(j)){
                                System.out.println("()");
                            }
                        }
                        for (int j = 1; j <= g; j++) {
                            if (i==matcher.start(j) && i!=matcher.end(j)){
                                System.out.print("(");
                            }
                        }
                        System.out.print(input.charAt(i));
                        for (int j = 1; j <= g; j++) {
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

    @Test
    public void testRegexGet() {
        String url = "http://util.regex.info/blog:9999";
        String regex = "(?x)^(https?)://([^/:]+)(?:(\\d+))?";
        Pattern compile = Pattern.compile(regex);
        Matcher m = compile.matcher(url);
        if (m.find()) {
            System.out.println(
                    "Overall [" + m.group() + " ] "
                            + " (from " + m.start() + " to " + m.end() + " )\n"
                            + " Protocol [" + m.group(1) + " ] "
                            + " (from " + m.start(1) + " to " + m.end(1) + " )\n"
                            + " Hostname [" + m.group(2) + " ] "
                            + " (from " + m.start(2) + " to " + m.end(2) + " )\n"
            );

            //group(3)可能为空，此处需要小心对待
            if (m.group(3) == null) {
                System.out.println("No port;default of '80' is assumed");
            } else {
                System.out.println(" Port [" + m.group(3) + " ] "
                        + " (from " + m.start(3) + " to " + m.end(3) + " )\n");
            }
        }
    }

    @Test
    public void testReplace() {
        String string1 = "-->one+test<--";
        String regex1 = "\\w+";
        String string = "he1lo1d34";
        String regex = "\\d";
        Matcher matcher = Pattern.compile(regex).matcher(string);
        String s = matcher.replaceAll("*");
        System.out.println(s);
        Matcher m = Pattern.compile(regex1).matcher(string1);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            //给定一个StringBuffer将替换之后的内容填充到sb中
            m.appendReplacement(sb, "XXX");
        }
        //结尾没有匹配的内容可以通过下面方法填充
        m.appendTail(sb);
        System.out.println(sb);

    }

    @Test
    public void testCSV() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream("d:/测试留言.csv"));
        String regex =//双引号字段保存到group(1),非引号字段保存到group(2)
                "\\G(?:^|,)                              \n" +
                        "(?:                                    \n" +
                        " #要么是双引号字段                       \n" +
                        " \" #开头双引号                         \n" +
                        "( [^\"]*+(?:\"\"[^\"]*+)*+)            \n" +
                        "\" #结束双引号                          \n" +
                        "| ([^\",]*+)                           \n" +
                        "           )                           \n";
        String line = sc.nextLine();
        Matcher mMain = Pattern.compile(regex).matcher(line);
        //创建匹配[""]的matcher，目标文本暂时为虚构
        Matcher mQuote = Pattern.compile("\"\"").matcher("");
        while (mMain.find()) {
            String field;
            if (mMain.start(2) > 0) {
                field = mMain.group(2);
            } else {
                field = mQuote.reset(mMain.group(1)).replaceAll("\"");
            }
            System.out.println("Field [ " + field + " ]");
        }
    }
}
