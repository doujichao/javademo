package 算法.递推算法.三角数;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StackTriangle2App {
	
	static int theNumber;
	static int theAnswer;
	static StackX theStack;
	
	public static void main(String[] args) {
		System.out.println("Enter a number :");
		theNumber=getInt();
		stackTriangle();
		System.out.println("Triangle="+theAnswer);
	}

	private static void stackTriangle() {
		theStack=new StackX(10000);
		theAnswer=0;
		while(theNumber>0) {
			theStack.push(theNumber);
			--theNumber;
		}
		while(!theStack.isEmpty()) {
			int newN=theStack.pop();
			theAnswer+=newN;
		}
	}

	private static int getInt() {
		String s=null;
		try {
			s = getString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(s);
	}

	private static String getString() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
}


class StackX{
	/**
	 * 栈最大大小
	 */
	private int maxSize;
	/**
	 * 栈空间
	 */
	private int[] stackArray;
	/**
	 * 栈顶指针
	 */
	private int top;
	public StackX(int s) {
		maxSize=s;
		stackArray=new int[maxSize];
		top=-1;
	}
	
	public void push(int p) {
		stackArray[++top]=p;
	}
	
	public int pop() {
		return stackArray[top--];
	}
	
	public int peek() {
		return stackArray[top];
	}
	
	public boolean isEmpty() {
		return top==-1;
	}
}