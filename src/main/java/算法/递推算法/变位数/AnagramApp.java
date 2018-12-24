package 算法.递推算法.变位数;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnagramApp {

	/**
	 *数组的大小 
	 */
	static int size;
	/**
	 * 所操作的次数
	 */
	static int count;
	/**
	 * 包含所有字串的数组
	 */
	static char[] arrChar=new char[100];
	
	public static void main(String[] args) {
		System.out.println("Enter a word:");
		String input=getString();//获取字符串
		size=input.length();//记录字符串的大小
		count=0;//初始化操作步数为0
		//记录字符串中的所有字符
		for (int i = 0; i < size; i++) {
			arrChar[i]=input.charAt(i);
		}
		//回文构词法
		doAnagram(size);
	}

	/**
	 *  使用回文构词法重组字符串
	 * @param newSize 数组长度
	 */
	private static void doAnagram(int newSize) {
		/**
		 * 如果数组长度为1的话直接返回
		 */
		if(newSize==1) {
			return ;
		}
		for (int i = 0; i < newSize; i++) {
			//从1-n的长度的字串都进行回文重构，从长度为n开始到长度为1结束
			doAnagram(newSize-1);
			//每次迭代之后返回的时候，newSize的值为2，也就是说，每次返回之后就直接打印
			if(newSize==2) {
				displayWord();
			}
			//旋转数组
			/**
			 * 什么情况下，会进行数组的旋转？
			 * 1、
			 */
			rotate(newSize);
		}
		
	}

	private static void displayWord() {
		if(count<99) {
			System.out.print(" ");
		}
		if(count<9) {
			System.out.print(" ");
		}
		System.out.print(++count+" ");
		for (int j = 0; j < size; j++) {
			System.out.print(arrChar[j]);
		}
		System.out.print(" ");
		System.out.flush();
		if(count%6==0) {
			System.out.println("");
		}
		
	}

	/**
	 * 重新排列数组，将数组元素按照插入排序的方法，将最后旋转制定位数的数组一次
	 * @param newSize
	 */
	private static void rotate(int newSize) {
		int j;
		int position=size-newSize;
		char temp=arrChar[position];
		for (j = position+1; j < size; j++) {
			arrChar[j-1]=arrChar[j];
		}
		arrChar[j-1]=temp;
		
	}

	private static String getString() {
        return getString();
    }
}
