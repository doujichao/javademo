package 算法.数据结构.队列;

public class Deque {

	/**
	 * 队列的最大容量
	 */
	private int maxSize;
	/**
	 * 队列的存储元素
	 */
	private long[] queArray;
	/**
	 * 队列的队尾
	 */
	private int front;
	/**
	 * 队列的队头
	 */
	private int rear;
	/**
	 * 元素的数量
	 */
	private int nItems;
	
	/**
	 * 初始化队列
	 * @param s
	 */
	public Deque(int s) {
		maxSize=s;
		queArray=new long[s];
		front=0;
		rear=-1;
		nItems=0;
	}
	
	/**
	 * 插入
	 * @param j
	 */
	public void insertRight(long j) {
		if(rear==maxSize-1) {
			rear=-1;
		}
		queArray[++rear]=j;
		nItems++;
	}
	
	public void insertLeft(long j) {
		if(front==0) {
			queArray[maxSize-1]=j;
		}else {
			queArray[--front]=j;
		}
		nItems++;
	}
	/**
	 * 移除队尾元素
	 * @return
	 */
	public long removeLeft() {
		long temp=queArray[front++];
		if(front==maxSize) {
			front=0;
		}
		nItems--;
		return temp;
	}
	/**
	 * 移除对头元素
	 * @return
	 */
	public long removeRight() {
		long temp=queArray[rear--];
		if(rear==-1) {
			rear=maxSize-1;
		}
		nItems--;
		return temp;
	}
	public long peekFront(){
		return queArray[front];
	}
	
	public boolean isEmpty() {
		return (nItems==0);
	}
	
	public boolean isFull() {
		return nItems==maxSize;
	}
	
	public int size() {
		return nItems;
	}
}
