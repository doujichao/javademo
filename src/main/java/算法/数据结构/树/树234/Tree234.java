package 算法.数据结构.树.树234;

public class Tree234 {

	private Node root=new Node();
	
	/**
	 * 查找节点
	 * @param key 带查找节点数据
	 * @return 返回查找位置
	 */
	public int find(long key) {
		Node curNode=root;
		int childNumber;
		while(true) {
			if((childNumber=curNode.findItem(key))!=-1) {
				return childNumber;
			}else if(curNode.isLeaf()) {
				return -1;
			}else {
				curNode=getNextChild(curNode,key);
			}
		}
	}

	/**
	 * 插入节点
	 * @param dValue
	 */
	public void insert(long dValue) {
		Node curNode=root;
		DataItem tempItem=new DataItem(dValue);
		while(true) {
			if(curNode.isFull()) {
				split(curNode);
				curNode=curNode.getParent();
				curNode=getNextChild(curNode, dValue);
			}else if(curNode.isLeaf()) {
				break;
			}else {
				curNode=getNextChild(root, dValue);
			}
		}
		curNode.insertItem(tempItem);
	}
	
	/**
	 * 切割字符节点
	 * @param curNode 待切割节点
	 */
	private void split(Node curNode) {
		DataItem itemB,itemC;
		Node parent,child2,child3;
		int itemIndex;
		//从当前节点中移除两个节点itemB,itemC
		itemC=curNode.removeItem();
		itemB=curNode.removeItem();
		//从当前节点中移除连个子链
		child2=curNode.disconnectChild(2);
		child3=curNode.disconnectChild(3);
		//构建一个新的右节点
		Node newRight=new Node();
		//如果当前节点是根节点的话
		if(curNode==root) {
			//新创建一个新节点
			root=new Node();
			//父节点变成root
			parent=root;
			//根节点连接当前节点成为第一个子节点
			root.connectChild(0, curNode);
		}else {
			//如果当前节点不是根节点的话，记录当前节点的父节点
			parent=curNode.getParent();
		}
		//父节点插入数据项B
		itemIndex=parent.insertItem(itemB);
		//获取父节点的总大小
		int n=parent.getNumItens();
		/**
		 * 重新连接子链
		 */
		for(int j=n-1;j>itemIndex;j--) {
			Node temp=parent.disconnectChild(j);
			parent.connectChild(j+1, temp);
		}
		//将右子链连接到父节点上
		parent.connectChild(itemIndex+1, newRight);
		//右链中插入数据项C
		newRight.insertItem(itemC);
		//右链的第一个位置连接孩子1
		newRight.connectChild(0, child2);
		//右链的第二个位置连接孩子2
		newRight.connectChild(1, child3);
		//这里保证了curNode，和newRight都拥有两个子链
	}

	/**
	 * 根据当前节点和给定节点获取下一个孩子节点
	 * @param curNode 当前节点
	 * @param key 给定数据值
	 * @return 下一个节点
	 */
	private Node getNextChild(Node curNode, long key) {
		int j;
		//返回当前数据项的数量
		int numItems=curNode.getNumItens();
		//遍历数据项
		for(j=0;j<numItems;j++) {
			/**
			 * 找到合适的数据项，如果待插入数据项比当前数据项的第j的数据项
			 * 小的话，将第j个子链返回
			 */
			if(key<curNode.getItem(j).dData) {
				return curNode.getChild(j);
			}
		}
		/**
		 * 如果没有找到比数据项小的数据，返回j+1的子链，这里j再for循环中已经加1了
		 */
		return curNode.getChild(j);
	}
	
	/**
	 * 显示树
	 */
	public void displayTree() {
		recDisplayTree(root,0,0);
	}

	private void recDisplayTree(Node thisNode, int level, int childNum) {
		System.out.print("level="+level+" child="+childNum+" ");
		thisNode.displayNode();
		
		int numItems=thisNode.getNumItens();
		for(int j=0;j<numItems+1;j++) {
			Node nextNode=thisNode.getChild(j);
			if(nextNode!=null) {
				recDisplayTree(nextNode, level+1, j);
			}else {
				return;
			}
		}
	}
}
