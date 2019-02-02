package 算法.test;



import java.io.File;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class Demo {

	
	public static void main(String[] args) {
		String property = Security.getProperty("security.provider.1");
		System.out.println(property);
		System.out.println("==================");
		for (Provider p : Security.getProviders()) {
			System.out.println(p);
			/*for (Map.Entry<Object, Object> entity : p.entrySet()) {
				System.out.println(entity.getKey());
			}*/
		}
		/*int int1 = Utils.getInt();
		System.out.println(RabbitSum.Fibonacci(int1));*/
		
		Map<Integer,String>map=new HashMap<Integer, String>();
		map.put(1, "年后");
	}
	
	@Test
	public void test() {
		int[] arr= {1,2,3,4,5,6,7,8};
		int[] bbb= {9,10,11,12,13,14,15,16};
		System.arraycopy(arr, 3, bbb, 2, 3);
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(bbb));
	}
	
	@Test
	public void test1() {
		char[] arr= {'+','-','*','/','^'};
		for (int i = 0; i < arr.length; i++) {
			System.out.println(new Integer(arr[i]));
		}
	}
	@Test
	public void test2() {
		String s=File.separator;
		System.out.println(s);
		List<Integer> list=new ArrayList<Integer>(8);
		System.out.println(list.size());
	}
	@Test
	public void test3() {
		int oldhash=879582733;
		int newhash=oldhash ^ (oldhash>>>16);
		System.out.println(oldhash);
		System.out.println(newhash);
		int n=newhash & 0xFFFF;
		System.out.println(n);
	}

	@Test
	public void test4(){
		long l=1L;
		for (int i=0;i<64;i++){
			System.out.println(Long.toBinaryString(l=l | (1L<<i)));
		}
	}
	
}
