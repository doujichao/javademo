package 算法.数据结构.树.树234;

/**
 * 2-3-4树的数据类
 * @author douji
 *
 */
public class DataItem {

	public long dData;
	
	/**
	 * 通过数据项构造datagram
	 * @param dd
	 */
	public DataItem(long dd) {
		dData=dd;
	}
	
	public void displayItem() {
		System.out.print("/"+dData);
	}
}
