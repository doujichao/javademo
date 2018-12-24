package 算法.数据结构.栈;

/**
 * 数据栈结构
 * @author douji
 *
 */
public class StackX {
	
	/***
	 * 最大栈空间
	 */
	private int maxSize;
	/**
	 * 栈实体
	 */
	private long[] stackArray;
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
		stackArray=new long[s];
		top=-1;
	}

	/**
	 * 入栈
	 * @param j
	 */
	public void push(long j) {
		if(!isFull()) {
			stackArray[++top]=j;
		}
		throw new StackOverflowError("栈已经满了");
	}
	/**
	 * 弹栈
	 * @return
	 */
	public long pop() {
		if(!isEmpty())
		{
			return stackArray[top--];
		}
		return -1;
	}
	/**
	 * 取出
	 * @return
	 */
	public long peek() {
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
