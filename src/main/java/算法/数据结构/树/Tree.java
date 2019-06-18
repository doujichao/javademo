package 算法.数据结构.树;

import java.util.Stack;

/**
 * 二叉查找树，满足条件
 * 1、只有一个根节点
 * 2、每个节点最多有两个子节点
 * 3、左子树<该节点<右子树
 * @author douji
 *
 */
public class Tree {

	/**
	 * 数的根节点
	 */
	protected Node root;

	/**
	 * 构造函数，初始化根root
	 */
	public Tree() {
		root=null;
	}

	/**
	 * 查找给定的值的节点
	 * @param key 节点的值
	 * @return 已经查找到的节点
	 */
	public Node find(int key) {
		//只有根节点可以访问其他节点，首先将根节点赋值给current
		Node current=root;
		//遍历查找树节点，当找到节点的时候返回
		while(current.iData!=key) {
			//如果节点值比key值小，查找左树
			if(key>current.iData) {
				current=current.rightChild;
				//如果节点值比key值大，查找右树
			}else {
				current=current.leftChild;
			}
			//如果current为null，说明遍历到了最后节点都没有找到
			if(current==null)return null;
		}
		return current;
	}

	/**
	 * 二叉搜索树插入，我们需要先找到对应节点的位置，然后将节点插入
	 * @param id 节点包含int数据
	 * @param dd 节点包含double数据
	 */
	public void insert(int id,double dd) {
		//这里是将id和dd这两个数据封装起来
		Node newNode=new Node(id,dd);
		//如果root值为null，我们可以直接将新节点赋值给root
		if(root==null) {
			root=newNode;
			//如果root节点不为空
		}else {
			//记录当前节点
			Node current=root;
			//这里需要一个新的节点parent来存储待插入节点的父节点，最后建立父节点和待插入节点之间的关系
			Node parent;
			//遍历所有节点，找到节点应该插入的位置
			while(true) {
				parent=current;
				if(id<current.iData) {
					current=current.leftChild;
					if(current==null) {
						parent.leftChild=newNode;
						return ;
					}
				}else {
					current =current.rightChild;
					if(current==null) {
						parent.rightChild=newNode;
						return ;
					}
				}
			}
		}
	}

	/**
     * 三种遍历方式以根节点在那个位置来命名，在第一个位置为前序遍历，第二个位置为中序遍历，在第三个位置为后序遍历
	 * 中序遍历二叉搜索树，最后遍历的结果是升序排列
	 * @param localRoot 给定的节点
	 */
	private void inOrder(Node localRoot) {
		/*
		 * 迭代终止条件：
		 * 当迭代到叶子节点的时候inOrder(localRoot.leftChild);还有
		 * inOrder(localRoot.rightChild);都不会执行，这样就终止了迭代
		 * 指打印了这个叶子节点，从而可以向上回溯
		 */
		if(localRoot!=null) {
			//遍历左子树
			inOrder(localRoot.leftChild);
			//访问这个节点
			System.out.println(localRoot.iData+" ");
			//遍历右子树
			inOrder(localRoot.rightChild);
		}
	}

	/**
	 * 前序遍历树，先访问根节点，再访问左子树，再访问右子树
	 * @param localRoot 给定的根
	 */
	private void preOrder(Node localRoot) {
		if(localRoot!=null) {
			//访问这个节点
			System.out.println(localRoot.iData+" ");
			//遍历左子树
			preOrder(localRoot.leftChild);
			//遍历右子树
			preOrder(localRoot.rightChild);
		}
	}
	/**
	 * 前序遍历树，先访问左子树，再访问右子树,最后访问根节点
	 * @param localRoot 给定的根
	 */
	private void postOrder(Node localRoot) {
		if(localRoot!=null) {
			//遍历左子树
			postOrder(localRoot.leftChild);
			//遍历右子树
			postOrder(localRoot.rightChild);
			//访问这个节点
			System.out.println(localRoot.iData+" ");
		}
	}

	public Node findMin() {
		Node current=root;
		Node last=null;
		while(current!=null) {
			last=current;
			current=current.leftChild;
		}
		return last;
	}

	/**
	 * 删除节点，最复杂的算法，分为三种情况
	 * 1、待删除的节点没有子节点
	 * 2、待删除的节点只有一个子节点
	 * 3、待删除的节点右两个子节点
	 * @param key 待删除的节点
	 */
	public boolean delete(int key) {
		Node current=root;
		Node parent=root;
		//这里需要知道这里时左子树，还是右子树，因为左子树和右子树删除的节点不一样
		boolean isLeftChild=true;

		//找到待删除的节点current还有父节点parent
		while(current.iData!=key) {
			parent=current;
			if(key<current.iData) {
				isLeftChild=true;
				current=current.leftChild;
			}else {
				isLeftChild=false;
				current=current.rightChild;
			}

			//没有找到
			if(current==null) {
				return false;
			}
		}


		//删除节点
		//1、没有子节点
		if(current.leftChild==null&&current.rightChild==null) {
			if(current==root) {
				root=null;
			}else if(isLeftChild) {
				parent.leftChild=null;
			}else {
				parent.rightChild=null;
			}

			//2、只有一个子节点
			//只有左节点
		}else if(current.rightChild==null){
			if(current==root) {
				root=current.leftChild;
			}else if(isLeftChild) {
				parent.leftChild=current.leftChild;
			}else {
				parent.rightChild=current.leftChild;
			}
			//只有右节点
		}else if(current.leftChild==null) {
			if(current==root) {
				root=current.rightChild;
			}else if(isLeftChild) {
				parent.leftChild=current.rightChild;
			}else {
				parent.rightChild=current.rightChild;
			}
		}else {
			/**
			 * 这里需要注意有两种情况：
			 *  1、后继节点可能直接就是待删除节点的右节点
			 *  2、后继节点是左节点，是左节点的时候，有没有右子树情况都是一样的
			 */
			Node successor = getSuccessor(current);
			if(current==root) {
				root=successor;
			}else if(isLeftChild) {
				parent.leftChild=successor;
			}else {
				parent.rightChild=successor;
			}
			//当前节点的左节点交给后继节点的左节点
			successor.leftChild=current.leftChild;
		}
		return true;
	}
	/**
	 * 找一个节点的后继节点
	 * @param delNode 待删除节点
	 * @return 后继节点
	 */
	private Node getSuccessor(Node delNode) {

		Node successorParent=delNode;
		Node successor=delNode;
		Node current=delNode.rightChild;
		while(current!=null) {
			successorParent=successor;
			successor=current;
			current=current.leftChild;
		}
		if(successor!=delNode.rightChild) {
			/**
			 * 这里的successor.rightChild可能为空，也可能不为空
			 * 但是都是同样的操作
			 */
			successorParent.leftChild=successor.rightChild;
			/**
			 * 将后继节点的右子树变成待删除节点的右子树
			 */
			successor.rightChild=delNode.rightChild;
		}
		return successor;
	}

	public void traverse(int traverseType) {
		switch(traverseType) {
		case 1:System.out.println("\nPreorder traversal:");
		preOrder(root);
		break;
		case 2:System.out.println("\nInorder traversal");
		inOrder(root);
		break;
		case 3:System.out.println("\nPostorder traversal");
		postOrder(root);
		break;
		}
	}

	public void displayTree() {
		Stack<Node> globalStack=new Stack<Node>();
		globalStack.push(root);
		int nBlanks=32;
		boolean isRowEmpty=false;
		System.out.println("-------------------------------------------");
		while(isRowEmpty==false) {
			Stack<Node> localStack=new Stack<Node>();
			isRowEmpty=true;

			for(int j=0;j<nBlanks;j++) {
				System.out.print(" ");
			}
			while(globalStack.isEmpty()==false) {
				Node temp=globalStack.pop();
				if(temp!=null) {
					System.out.print(temp.iData);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					if(temp.leftChild!=null||temp.rightChild!=null) 
						isRowEmpty=false;
				}else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}

				for(int j=0;j<nBlanks*2-2;j++) 
					System.out.print(" ");
			}
			System.out.println();
			nBlanks/=2;
			while(localStack.isEmpty()==false) 
				globalStack.push(localStack.pop());
		}
		System.out.println("------------------------------------------------");
	}
}

