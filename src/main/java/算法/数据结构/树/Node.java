package 算法.数据结构.树;

public class Node {
	
	
	
	int iData;
	double dData;
	Node leftChild;
	Node rightChild;
	Node parent;
	
	boolean color;
	
	public Node(int id, double dd) {
		this.iData=id;
		this.dData=dd;
		color=true;
	}

	void display() {
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("}");
	}
}
