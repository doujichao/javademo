package 算法.数据结构.链表;

public class LinkApp {
	public static void main(String[] args) {
		LinkList theList=new LinkList();
		theList.insertFirst(22, 2.99);
		theList.insertFirst(44, 4.99);
		theList.insertFirst(66, 6.99);
		theList.insertFirst(88, 8.99);
		
		theList.displayList();
		
		while(!theList.isEmpty()) {
			Link link = theList.deleteFirst();
			System.out.println("Deleted ");
			link.displayLink();
		}
		theList.displayList();
	}
}
