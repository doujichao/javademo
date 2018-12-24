package 算法.数据结构.链表.双端链表;


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
class FirstLastList{
	/**
	 * 链表头
	 */
	private Link first;
	private Link last;
	/**
	 * 无参构造将链表头初始化为null
	 */
	public FirstLastList() {
		first=null;
		last=null;
	}
	/**
	 * 判断链表是否为空
	 * @return true为空，false不为空
	 */
	public boolean isEmpty() {
		return (first==null);
	}
	/**
	 * 插入头部元素
	 * @param id int数据id
	 * @param dd double数据
	 */
	public void insertFirst(long dd) {
		Link newLink=new Link(dd);
		//这里如果first为null的话，则表示最后一个元素的next为空
		if(isEmpty()) {
			last=newLink;
		}
		newLink.next=first;
		first=newLink;
	}
	/**
	 * 插入头部元素
	 * @param id int数据id
	 * @param dd double数据
	 */
	public void insertLast(long dd) {
		Link newLink=new Link(dd);
		//这里如果first为null的话，则表示最后一个元素的next为空
		if(isEmpty()) {
			first=newLink;
		}else {
			last.next=newLink;
		}
		last=newLink;
	}
	
	/**
	 * 删除头部元素
	 * @return
	 */
	public long deleteFirst() {
		long temp=first.dData;
		if(first.next==null) {
			last=null;
		}
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