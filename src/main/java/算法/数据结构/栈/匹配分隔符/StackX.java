package 算法.数据结构.栈.匹配分隔符;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StackX {


	/***
	 * 最大栈空间
	 */
	private int maxSize;
	/**
	 * 栈实体
	 */
	private char[] stackArray;
	/**
	 * 栈顶元素指针
	 */
	private int top;
	
	/**
	 * 初始化栈
	 * @param s
	 */
	public StackX(int s) {
		maxSize=s;
		stackArray=new char[s];
		top=-1;
	}

	/**
	 * 入栈
	 * @param j
	 */
	public void push(char j) {
		if(!isFull()) {
			stackArray[++top]=j;
		}
		throw new StackOverflowError("栈已经满了");
	}
	/**
	 * 弹栈
	 * @return
	 */
	public char pop() {
		if(!isEmpty())
		{
			return stackArray[top--];
		}
		return '0';
	}
	/**
	 * 取出
	 * @return
	 */
	public char peek() {
		return stackArray[top];
	}
	/**
	 * 栈是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return top==-1;
	}
	/**
	 * 栈是否满
	 * @return
	 */
	public boolean isFull() {
		return top==maxSize-1;
	}
	/**
	 * 清空栈
	 */
	public void clear() {
		top=-1;
	}
	
}	

class BracketChecker{
	private String input;
	public BracketChecker(String in) {
		input =in;
	}
	
	public void check() {
		int stackSize = input.length();
		StackX theStack=new StackX(stackSize);
		for (int i = 0; i < stackSize; i++) {
			char ch=input.charAt(i);
			switch(ch) {
			case '{':
			case '[':
			case '(':
				theStack.push(ch); break;
			case '}':
			case ']':
			case ')':
				if(!theStack.isEmpty()) {
					char chx = theStack.pop();
					if((ch=='}'&&chx=='{')||
						(ch==']'&&chx=='['||
						ch==')'&&chx==')')	) {
						System.out.println("Error:"+ch+" at"+i);
					}
				}else {
					System.out.println("Error:"+ch+" at"+i);
				}
			default:
				break;
			}
		}
		if(!theStack.isEmpty()) {
			System.out.println("Error:missing right delimiter");
		}
	}
}

class BracketApp{
	public void test() {
		String input;
		while(true) {
			System.out.println("Enter String containing delimiters:");
			System.out.flush();
			input = getString();
			if(input.equals("")) {
				break;
			}
			BracketChecker b=new BracketChecker(input);
			b.check();
		}
	}
	
	public static String getString() {
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		try {
			String string = br.readLine();
			return string;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}