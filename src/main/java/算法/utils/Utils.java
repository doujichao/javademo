package 算法.utils;

import java.util.Scanner;

public class Utils {

	private static final Scanner sc=new Scanner(System.in);
	
	public static String getString() {
		System.out.println("请输入字符串：");
		return sc.nextLine();
	}
	public static int getInt() {
		System.out.println("请输入数字：");
		return sc.nextInt();
	}
	
	public static void close() {
		sc.close();
	}
	
	/**
	 * 直接交换两个数字引用
	 * @param a
	 * @param b
	 */
	public static void doChange(int a,int b) {
		int temp=a;
		a=b;
		b=temp;
	}
	
	/**
	 * 交换数组两个数位置
	 * @param arrs
	 * @param a
	 * @param b
	 */
	public static void doChange(int[] arrs,int a,int b) {
		int temp=arrs[a];
		arrs[a]=arrs[b];
		arrs[b]=temp;
	}
}
