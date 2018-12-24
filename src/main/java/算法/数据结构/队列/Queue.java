package 算法.数据结构.队列;

public class Queue {

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
	public Queue(int s) {
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
	public void insert(long j) {
		if(rear==maxSize-1) {
			rear=-1;
		}
		queArray[++rear]=j;
		nItems++;
	}
	/**
	 * 移除队尾元素
	 * @return
	 */
	public long remove() {
		long temp=queArray[front++];
		if(front==maxSize) {
			front=0;
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
