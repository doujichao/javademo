package 算法.security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.junit.Test;

public class Demo {

	public static  String FLAG="<beans>" + 
			"	<bean name=\"fileHelloWorld\" class=\"com.openv.springdemo2.HelloWorld\">" + 
			"		<constructor-arg>" + 
			"			<ref bean=\"fileHello\"/>" + 
			"		</constructor-arg>" + 
			"	</bean>" + 
			"	<bean name=\"fileHello\" class=\"com.openv.springdemo2.FileHelloStr\">" + 
			"		<constructor-arg>" + 
			"			<value>helloworld.properties</value>" + 
			"		</constructor-arg>" + 
			"	</bean>" + 
			"</beans>";

	
	@Test
	public void test() throws Exception {
		Properties prop=new Properties();
		prop.load(new FileInputStream("name.properties"));
		prop.setProperty("sex", "未知");
		prop.store(new FileWriter("name1.properties"), "");
		String name = prop.getProperty("name");
		System.out.println(name);
	}
	@Test
	public void test1() throws Exception {
		FLAG=FLAG.replaceAll("	", "");
		String regex="<[\\w-/]*";
		String[] split = FLAG.split(regex);
		System.out.println(Arrays.toString(split));
		System.out.println("<value>helloworld.properties</value>".matches("(<[/a-z]*>)?\\w*\\.\\w*(<[/a-z]*>)?"));
	}
	@Test
	public void test2() throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("name.1txt")));
		String s=null;
		String regex="(<[/a-z]*>)?(\\w*(\\.)?\\w*)*(<[/a-z]*>)?";
		int i=0;
		List<String> list=new ArrayList<String>();
		Map<String,String>map=new HashMap<String, String>();
		while((s=br.readLine())!=null) {
			i++;
			if(s.trim().matches(regex)) {
				s=s.trim().replaceAll("<[/a-z]*>", "");
				if(s!=null&&!s.equals(""))
					list.add(s);
			}else {
				String[] split = s.trim().split(" ");
				for (String string : split) {
					if(string.matches(".*=.*")) {
						String[] split2 = string.split("=");
						map.put(split2[0]+i, split2[1]);
					}
				}
			}
		}
		for (String string : list) {
			System.out.println(string);
		}
		for (String ss : map.keySet()) {
			System.out.println(ss+"========"+map.get(ss));
		}
		br.close();
	}
}
