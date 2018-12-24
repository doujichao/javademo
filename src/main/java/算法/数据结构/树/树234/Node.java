package 算法.数据结构.树.树234;

/**
 * 2-3-4树的节点类
 * @author douji
 *
 */
public class Node {

	/**
	 * 节点的最大大小
	 */
	private static final int ORDER=4;
	/**
	 * 包含数据的数量
	 */
	private int numItems;
	/**
	 * 父节点
	 */
	private Node parent;
	/**
	 * 2-3-4树的字节点的集合，初始化大小为4，因为最多
	 * 有4个子链接点
	 */
	private Node[] childArray=new Node[ORDER];
	/**
	 * 数据项数组，用于存储保存在节点中的数据项
	 */
	private DataItem[] itemArray=new DataItem[ORDER-1];
	
	/**
	 * 连接孩子节点到这个节点
	 * @param childNum 孩子节点的位置
	 * @param child 孩子节点
	 */
	public void connectChild(int childNum,Node child) {
		childArray[childNum]=child;
		if(child!=null) {
			child.parent=this;
		}
	}
	/**
	 * 断开和父节点的连接
	 * @param childNum 需要断开的位置
	 * @return 断开的节点
	 */
	public Node disconnectChild(int childNum) {
		Node temNode=childArray[childNum];
		childArray[childNum]=null;
		return temNode;
	}
	
	/**
	 * 获取第几个孩子节点
	 * @param childNum 孩子节点的位置
	 * @return
	 */
	public Node getChild(int childNum) {
		return childArray[childNum];
	}
	/**
	 * 获取父亲节点
	 * @return
	 */
	public Node getParent() {
		return parent;
	}
	/**
	 * 是否是叶子节点
	 * @return
	 */
	public boolean isLeaf() {
		return childArray[0]==null;
	}
	/**
	 * 有多少个数据项
	 * @return 数据项的个数
	 */
	public int getNumItens() {
		return numItems;
	}
	/**
	 * 根据下标返回第几个数据项
	 * @param index 数据项的下标
	 * @return 返回对应的数据项
	 */
	public DataItem getItem(int index) {
		return itemArray[index];
	}
	/**
	 * 该节点是否是满的节点
	 * @return 返回是否是满的
	 */
	public boolean isFull() {
		return numItems==ORDER-1;
	}
	
	/**
	 * 查看该节点中是否存在给定数据项
	 * @param key 给定数据项
	 * @return 如果存在返回给定数据项的下标，
	 * 			如果不存在，返回-1
	 */
	public int findItem(long key) {
		for(int j=0;j<ORDER-1;j++) {
			if(itemArray[j]==null)
				break;
			else if(itemArray[j].dData==key)
				return j;
		}
		return -1;
	}
	/**
	 * 插入数据项
	 * @param newItem 新数据项
	 * @return 返回插入的位置
	 */
	public int insertItem(DataItem newItem) {
		//包含数据的数量加1
		numItems++;
		//取出数据项
		long newKey=newItem.dData;
		//有点像插入排序，倒叙查找比待插入
		for(int j=ORDER-2;j>=0;j--) {
			if(itemArray[j]==null) {
				continue;
			}else {
				long itsKey=itemArray[j].dData;
				if(newKey<itsKey) {
					itemArray[j+1]=itemArray[j];
				}else {
					itemArray[j+1]=newItem;
					return j+1;
				}
			}
		}
		
		itemArray[0]=newItem;
		return 0;
	}
	
	/**
	 * 删除最大项
	 * @return 已经删除的最大项
	 */
	public DataItem removeItem() {
		DataItem temp=itemArray[numItems-1];
		itemArray[numItems-1]=null;
		numItems--;
		return temp;
	}
	
	public void displayNode() {
		for(int j=0;j<numItems;j++) {
			itemArray[j].displayItem();
		}
		System.out.println("/");
	}
}
