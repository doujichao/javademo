package 算法.数据结构.链表;

public class Link {

	/**
	 * int类型数据
	 */
	public int iData;
	/**
	 * double类型数据
	 */
	public double dData;
	/**
	 * 只想下一个连接点的地址
	 */
	public Link next;
	public Link(int id,double dd) {
		iData=id;
		dData=dd;
	}
	
	public void displayLink() {
		System.out.println("{"+iData+", "+dData+"}");
	}
}

/**
 * 循环链表
 * @author douji
 *
 */
class LinkList{
	/**
	 * 链表头
	 */
	private Link first;
	/**
	 * 无参构造将链表头初始化为null
	 */
	public LinkList() {
		first=null;
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
	public void insertFirst(int id,double dd) {
		Link newLink=new Link(id,dd);
		//这里如果first为null的话，则表示最后一个元素的next为空
		newLink.next=first;
		first=newLink;
	}
	
	/**
	 * 删除头部元素
	 * @return
	 */
	public Link deleteFirst() {
		Link temp=first;
		first=temp.next;
		return temp;
	}
	
	/**
	 * 查找指定节点
	 * @param key
	 * @return
	 */
	public Link find(int key) {
		Link current =first;
		while(current.iData!=key) {
			if(current.next==null) {
				return null;
			}else {
				current=current.next;
			}
		}
		return current;
	}
	
	public Link delete(int key) {
		Link current =first;
		Link previous=first;
		while(current.iData!=key) {
			if(current.next==null) {
				return null;
			}else {
				previous=current;
				current=current.next;
			}
		}
		if(current==first) {
			first=first.next;
		}else {
			//短路
			previous.next=current.next;
		}
		return current;
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