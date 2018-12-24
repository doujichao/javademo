package 算法.数据结构.链表.插入排序;


public class Link {

	/**
	 * double类型数据
	 */
	public long dData;
	/**
	 * 只想下一个连接点的地址
	 */
	public Link next;
	public Link(long dd) {
		dData=dd;
	}
	
	public void displayLink() {
		System.out.println("{"+dData+"}");
	}
}

/**
 * 循环链表
 * @author douji
 *
 */
class SortedList{
	/**
	 * 链表头
	 */
	private Link first;
	/**
	 * 无参构造将链表头初始化为null
	 */
	public SortedList() {
		first=null;
	}
	
	public SortedList(Link[] linkArr) {
		first=null;
		for (int i = 0; i < linkArr.length; i++) {
			insert(linkArr[i]);
		}
	}
	/**
	 * 判断链表是否为空
	 * @return true为空，false不为空
	 */
	public boolean isEmpty() {
		return (first==null);
	}
	
	
	/**
	 * 插入连接点
	 * @param k
	 */
	public void insert(Link k) {
		Link previous=null;
		Link current=first;
		while(current!=null&&k.dData>current.dData) {
			previous=current;
			current=current.next;
		}
		//空链表
		if(previous==null) {
			first=k;
		}else {
			previous.next=k;
		}
		k.next=current;
	}
	/**
	 * 插入头部元素
	 * @param key int数据id
	 * @param dd double数据
	 */
	public void insertFirst(long key) {
		Link newLink=new Link(key);
		Link previous=null;
		Link current=first;
		while(current!=null&&key>current.dData) {
			previous=current;
			current=current.next;
		}
		if(previous==null) {
			first=newLink;
		}else {
			previous.next=newLink;
		}
	}
	/**
	 * 删除头部元素
	 * @return
	 */
	public Link remove() {
		Link temp=first;
		first=first.next;
		return temp;
	}
	
	/**
	 * 显示链表
	 */
	public void displayList() {
		System.out.println("List (first -- > last ):");
		Link current =first;
		//递归显示链表数
		while(current!=null) {
			current.displayLink();
			current=current.next;
		}
		System.out.println("");
	}
	
}
