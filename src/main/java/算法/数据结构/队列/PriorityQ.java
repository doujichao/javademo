package 算法.数据结构.队列;

public class PriorityQ {

	/**
	 * 队列大小
	 */
	private int maxSize;
	/**
	 * 存放数据数组
	 */
	private long[] queryArray;
	/**
	 * 队列中元素个数
	 */
	private int nItems;
	
	public PriorityQ(int s) {
		queryArray=new long[s];
		maxSize=s;
		nItems=0;
	}
	public void insert(long item) {
		int j;
		if(nItems==0) {
			queryArray[nItems++]=item;
		}else {
			for(j=nItems-1;j>=0;j--) {
				if(item>queryArray[j]) {
					queryArray[j+1]=queryArray[j];
				}else {
					break;
				}
			}
			queryArray[j+1]=item;
			nItems++;
		}
	}
	
	public long remove() {
		return queryArray[--nItems];
	}
	public long peekMin() {
		return queryArray[nItems-1];
	}
	public boolean isEmpty() {
		return nItems==0;
	}
	public boolean isFull() {
		return nItems==maxSize;
	}
}
