package 算法.数据结构.链表.迭代器;


public class LinkIterator {
	
	private Link current;
	private Link previous;
	private LinkList ourList;

	public LinkIterator(LinkList linkList) {
		ourList=linkList;
		reset();
	}

	public void reset() {
		current=ourList.getFirst();
		previous=null;
	}
	public boolean atEnd() {
		return current.next==null;
	}

	public void nextLink() {
		previous=current;
		current=current.next;
	}
	
	public Link getCurrent() {
		return current;
	}
	public void insertAfter(long dd) {
		Link newLink=new Link(dd);
		if(ourList.isEmpty()) {
			ourList.setFirst(newLink);
			current=newLink;
		}else {
			newLink.next=current.next;
			current.next=newLink;
			nextLink();
		}
	}
	public void insertBefore(long dd) {
		Link newLink=new Link(dd);
		if(previous==null) {//在list的头部
			newLink.next = ourList.getFirst();
			ourList.setFirst(newLink);
			reset();
		}else {
			newLink.next=previous.next;
			previous.next=newLink;
			current=newLink;
		}
	}
	
	public long deleteCurrent() {
		long value = current.dData;
		if(previous==null) {
			ourList.setFirst(current.next);
			reset();
		}else {
			previous.next=current.next;
			if(atEnd()) {
				reset();
			}else {
				current=current.next;
			}
		}
		return value;
	}
}

class Link {
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
	
	public Link getFirst() {
		return first;
	}
	public void setFirst(Link f) {
		first=f;
	}
	/**
	 * 判断链表是否为空
	 * @return true为空，false不为空
	 */
	public boolean isEmpty() {
		return (first==null);
	}
	
	public LinkIterator getIterator() {
		return new LinkIterator(this);
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

