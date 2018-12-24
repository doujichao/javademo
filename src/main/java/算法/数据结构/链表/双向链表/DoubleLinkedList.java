package 算法.数据结构.链表.双向链表;


/**
 * 双向链表
 * @author douji
 *
 */
public class DoubleLinkedList {
	
	private Link first;
	private Link last;
	public DoubleLinkedList() {
		first=null;
		last=null;
	}
	
	public boolean isEmpty() {
		return first==null;
	}
	
	public void insertFirst(long dd) {
		Link newLink =new Link(dd);
		
		if(isEmpty()) {
			last=newLink;
		}else {
			first.previous=newLink;
		}
		newLink.next=first;
		first=newLink;
	}
	
	public void insertLast(Long dd) {
		Link newLink=new Link(dd);
		if(isEmpty()) {
			first=newLink;
		}else {
			//建立旧last和插入元素之间的关系
			last.next=newLink;
			newLink.previous=last;
		}
		last=newLink;
	}
	
	public Link deleteFirst() {
		Link temp=first;
		if(first.next==null) {
			//只有一个元素
			last=null;
		}else {
			first.next.previous=null;
		}
		first=first.next;
		return temp;
	}
	/**
	 * 删除最后一个元素
	 * @return
	 */
	public Link deleteLast() {
		Link temp=last;
		if(first.next==null) {
			//只有一个元素
			first=null;
		}else {
			last.previous.next=null;
		}
		last=last.previous;
		return temp;
	}

	public boolean insertAfter(long key,long dd) {
		Link current=first;
		while(current.dData!=key) {
			current=current.next;
			if(current==null) {
				return false;
			}
		}
		Link newLink=new Link(dd);
		if(current==last) {
			newLink.next=null;
			last=newLink;
		}else {
			newLink.next=current.next;
			current.next.previous=newLink;
		}
		newLink.previous=current;
		current.next=newLink;
		return true;
	}
	
	public Link deleteKey(long key) {
		Link current=first;
		while(current.dData!=key) {
			current=current.next;
			if(current==null) {
				return null;
			}
		}
		if(current==first) {
			first=current.next;
		}else {
			//将当前元素所在的地方短路
			current.previous.next=current.next;
		}
		//如果当前元素是最后一个元素，删除该元素
		if(current==last) {
			last=current.previous;
		}else {
			current.next.previous=current.previous;
		}
		return current;
		
	}
	public void displayForward() {
		System.out.println("List(first-->last)");
		Link current=first;
		while(current!=null) {
			current.displayLink();
			current=current.next;
		}
		System.out.println("");
	}
	public void displayBackward() {
		System.out.println("List(first-->last)");
		Link current=last;
		while(current!=null) {
			current.displayLink();
			current=current.previous;
		}
		System.out.println("");
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
	
	private class Link{
		public long dData;
		public Link next;
		public Link previous;
		public Link(long dData) {
			this.dData = dData;
		}
		public void displayLink() {
			System.out.println(dData+" ");
		}
		
	} 
	
}
