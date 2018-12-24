package 算法.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StringDemo {

	private static final String FLAG="fjaiihhioohglllllllllllloiohuhuhuhhh";

	@Test
	public void test() {
		String[] split = FLAG.split("");
		List<String> list=new ArrayList<String>();
		for (String string : split) {
			if(!list.contains(string)) {
				list.add(string);
			}
		}
		StringBuffer s=new StringBuffer();
		for (String string : list) {
			s.append(string);
		}
		System.out.println(s);
	}
}
