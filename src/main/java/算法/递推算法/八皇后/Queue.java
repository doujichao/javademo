package 算法.递推算法.八皇后;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Queue {
	@Test
	public void test() {
		Queue q=new Queue();
		q.queueApp();
	}
	

	public  Object[][] checkerboard=new Object[8][8];

	public List<Node> queue=new ArrayList<Node>(8);

	public List<Node> truequeue=new ArrayList<Node>();

	/**
	 * 初始化
	 */
	public Queue() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				checkerboard[i][j]=new Node(i,j);
			}
		}
	}

	/**
	 * 改变颜色
	 * @param n
	 */
	public void changeColor(Node n) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Node nn=(Node)checkerboard[i][j];
				if(nn.x==n.x||nn.y==n.y||(nn.x+nn.y)==(n.x+n.y)
						||(nn.x-nn.y)==(n.x-n.y)) {
					nn.isVisable=false;
					nn.num=nn.num+1;
				}
			}
		}
	}

	/**
	 * 退步的时候进行还原颜色
	 * @param n
	 */
	public void resetColor(Node n) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Node nn=(Node)checkerboard[i][j];
				if(nn.x==n.x||nn.y==n.y||(nn.x+nn.y)==(n.x+n.y)
						||(nn.x-nn.y)==(n.x-n.y)) {
					if(nn.num==1) {
						nn.num=0;
						nn.isVisable=true;
					}else {
						nn.num=nn.num-1;
					}
				}
			}
		}
	}

	/**
	 * 重新设置
	 */
	public void resetColor() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Node nn=(Node)checkerboard[i][j];
				nn.isVisable=true;
				nn.num=0;
			}
		}
	}



	public int queueApp() {
		int result=0;

		//遍历从这里开始
		row :for(int i=0;i<8;i++) {//这里表示遍历的行数
			if(i==0) {
				//如果已经遍历到第一行，打印一个换行
				System.out.println();
			}
			for(int j=0;;j++) {
				/**
				 * 当前行已经遍历到尽头，说明已经走到死胡同，需要回退一步
				 */
				if(j==8) {
					//如果已经折返到第一行尽头，那么直接返回
					if(i==0) {
						System.out.println();
						break row;
					}
					i=i-2;//退回一行
					/**
					 * 这里需要从集合中移除一个数据，如果改变这个数据的一些属性
					 */
					Node pop = queue.remove(queue.size()-1);
					//将因为这个元素改变的颜色还原
					resetColor(pop);
					/**
					 * 因为下次还会遍历这一行，先将这个元素变得不能访问到
					 */
					pop.num=1;
					pop.isture=true;
					pop.isVisable=false;
					/**
					 * 清除上一行因为前一个原因导致的新加改变颜色数量
					 */
					for(int h=0;h<8;h++) {
						Node node = (Node)checkerboard[i+2][h];
						if(node.isture) {
							node.num=node.num-1;
							node.isture=false;
							node.isVisable=true;
						}
					}
					break;
				}
				//获取当前元素
				Node nn=(Node)checkerboard[i][j];
				//如果当前元素可以访问
				if(nn.isVisable) {
					//添加进集合
					queue.add(nn);
					/**
					 * 改变颜色，遍历整个数组，如果和当前元素同行，同列，或者同对角线
					 * 就改变颜色为false，同时改变颜色操作数加一
					 */
					changeColor(nn);
					//如果i=7，说明现在已经找到一组符合的组合，现在可以打印输入
					if(i==7) {
						result++;
						for(int mm=0;mm<queue.size();mm++) {
							Node n = queue.get(mm);
							System.out.print("("+n.x+","+n.y+")");
						}
						/**
						 * 为了防止最后一行还有可以符合条件的元素，将最后一个元素移除
						 * 这个元素可访问条件变成false
						 */
						Node node = queue.remove(queue.size()-1);
						i=i-1;
						resetColor(node);
						node.num=1;
						node.isture=true;
						node.isVisable=false;
						System.out.println();

					}
					break;
				} 
			}
		}
		System.out.println("此时有"+result+"种情况");
		return result;
	}
	
	class Node {

		public  Integer y;
		public  Integer x;
		public  boolean isVisable=true;
		public Integer num;
		/**
		 * 该节点当前循环是否被使用过
		 */
		public boolean isture=false;
		public Node(int x,int y) {
			this.x=x;
			this.y=y;
			num=0;
		}
	}
}
