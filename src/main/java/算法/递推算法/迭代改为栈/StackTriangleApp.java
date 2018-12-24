package 算法.递推算法.迭代改为栈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StackTriangleApp {

	/**
	 * 确定输入的数字
	 */
	static int theNumber;
	/**
	 * 返回的最后答案
	 */
	static int theAnswer;
	/**
	 * 用于操作的数据栈
	 */
	static StackX theStack;
	/**
	 * 规定的数据步骤
	 */
	static int codePart;
	/**
	 * 包含值和栈顶返回值
	 */
	static Params theseParams;
	
	public static void main(String[] args) {
		System.out.println("Enter a number:");
		theNumber=getInt();
		recTriangle();
		System.out.println("Triangle="+theAnswer);
	}

	private static void recTriangle() {
		//初始化工作栈为10000
		theStack=new StackX(10000);
		//当前执行的初始步骤
		codePart=1;
		//当没有执行完的时候继续执行
		while(step()==false);
	}

	private static boolean step() {
		switch(codePart) {
		case 1:
			/**
			 * 确定栈底元素为6
			 * 将栈底元素压入栈
			 * 转入第二步
			 */
			System.out.print("case 1.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			theseParams=new Params(theNumber,6);
			theStack.push(theseParams);
			codePart=2;
			break;
		case 2:
			/**
			 * 查看栈顶元素，
			 * 如果栈顶元素的n值为1，则将theAnswer值变为1，转向第5步
			 * 否则直接转向第3步
			 */
			System.out.print("case 2.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			theseParams= theStack.peek();
			if(theseParams.n==1) {
				theAnswer=1;
				codePart=5;
			}else {
				codePart=3;
			}
			break;
		case 3:
			/**
			 * 重新创建一个Params元素，使用上一步查出的栈顶元素进行压栈，返回第2步
			 */
			System.out.print("case 3.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			Params newParams=new Params(theseParams.n-1,4);
			theStack.push(newParams);
			codePart=2;
			break;
		case 4:
			System.out.print("case 4.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			theseParams=theStack.peek();
			theAnswer=theAnswer+theseParams.n;
			codePart=5;
			break;
		case 5:
			/**
			 * 第2步跳转过来，此时满足条件就是栈顶元素n值为1
			 * 
			 * 操作：
			 * 1、重新查看一次栈顶元素，
			 * 2、将栈顶元素的返回地址4，赋值给codePart
			 * 3、弹出栈顶元素
			 */
			System.out.print("case 5.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			theseParams=theStack.peek();
			codePart=theseParams.returnAddress;
			theStack.pop();
			break;
		case 6:
			System.out.print("case 6.theAnswer="+theAnswer+" Stack:" );
			theStack.display();
			return true;
		}
		return false;
	}

	private static int getInt() {
		String s=getString();
		return Integer.parseInt(s);
	}

	private static String getString() {
        return getString();
    }
	
}

class Params{
	public int n;
	public int returnAddress;
	
	public Params(int nn,int ra) {
		n=nn;
		returnAddress=ra;
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
	private Params[] stackArray;
	/**
	 * 栈顶指针
	 */
	private int top;
	public StackX(int s) {
		maxSize=s;
		stackArray=new Params[maxSize];
		top=-1;
	}
	
	public void push(Params p) {
		stackArray[++top]=p;
	}
	
	public Params pop() {
		return stackArray[top--];
	}
	
	public Params peek() {
		return stackArray[top];
	}
	public void display() {
		if(top>=0) {
			for (int i = 0; i < top; i++) {
				System.out.print("("+(stackArray[i]).n+","+(stackArray[i]).returnAddress+")  ");
			}
		}
		System.out.println();
	}
}