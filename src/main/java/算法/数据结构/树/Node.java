package 算法.数据结构.树;

public class Node {

    public static final boolean BLACK=false;
    public static final boolean RED=true;

	int iData;
	double dData;
	Node leftChild;
	Node rightChild;
	Node parent;
	
	boolean color=BLACK;

	/**
	 * 对于红黑树来说，新增节点都是红色的
	 * @param id
	 * @param dd
	 */
	public Node(int id, double dd) {
		this.iData=id;
		this.dData=dd;
		color=RED;
	}

	public boolean isRed(){
		return color;
	}

	public void changeColor(){
	    color=!color;
    }

	void display() {
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("}");
	}
}
