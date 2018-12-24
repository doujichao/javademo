package 算法.数据结构.栈.四则运算;


public class StackX {

	/**
	 * 最大大小
	 */
	@SuppressWarnings("unused")
	private int maxSize;
	/**
	 * 数据数组
	 */
	private char[] stackArray;
	/**
	 * 栈顶下标
	 */
	private int top;
	
	public StackX(int s) {
		maxSize=s;
		stackArray=new char[s];
		top=-1;
	}
	
	public void push(char j) {
		stackArray[++top]=j;
	}
	
	public char pop() {
		return stackArray[top--];
	}
	public char peek() {
		return stackArray[top];
	}
	public boolean isEmpty() {
		return top==-1;
	}
	public int size() {
		return top+1;
	}
	public char peekN(int n) {
		return stackArray[n];
	}
	public void displayStack(String s) {
		System.out.println(s);
		System.out.println("Stack (bottom -- > top):");
		for (int j = 0; j < size(); j++) {
			System.out.print(peekN(j));
			System.out.print(' ');
		}
		System.out.println("");
	}
}

class InToPost{
	private StackX theStack;
	private String input;
	private String output="";
	
	public InToPost(String in) {
		input = in;
		int stackSize=input.length();
		theStack=new StackX(stackSize);
	}
	
	public String doTrans() {
		for(int j=0;j<input.length();j++) {
			char ch=input.charAt(j);
			theStack.displayStack("For "+ch+" ");
			switch(ch) {
			case '+':
			case '-':
				gotOper(ch,1);
				break;
			case '*':
			case '/':
				gotOper(ch,2);
				break;
			case '(':
				theStack.push(ch);
				break;
			case ')':
				theStack.push(ch);
				break;
			default:
				output =output+ch;
				break;
			}
		}
		while(!theStack.isEmpty()) {
			theStack.displayStack("While");
			output=output+theStack.pop();
		}
		theStack.displayStack("End ");
		return output;
	}
	
	public void gotOper(char opThis,int prec1) {
		while(!theStack.isEmpty()) {
			char opTop = theStack.pop();
			if(opTop=='(') {
				theStack.push(opTop);
				break;
			}else {
				int prec2;//优先级
				if(opTop=='+'||opTop=='-') {
					prec2=1;
				}else {
					prec2=2;
				}
				if(prec2<prec1) {
					theStack.push(opTop);
					break;
				}else {
					output=output+opTop;
				}
			}
		}
		theStack.push(opThis);
	}
	
	public void gotParam(char ch) {
		while(!theStack.isEmpty()) {
			char chx=theStack.pop();
			if(chx=='(')
				break;
			else
				output=output+chx;
		}
	}
}

