package 算法.递推算法.二分查找法;

public class OrdArray {

	/**
	 * 用与存放数据项的数组
	 */
	private long[] a;
	/**
	 * 数据向的个数
	 */
	private int nElems;
	
	public OrdArray(int max) {
		a=new long[max];
		nElems=0;
	}
	
	public int size() {
		return nElems;
	}
	
	public int find(long searchKey) {
		return recFind(searchKey,0,nElems-1);
	}

	/**
	 * 根据提供的搜索关键字，较小的边界，较大的边界来搜索关键字的下标
	 * @param searchKey 搜索关键字
	 * @param lowerBound 较小的边界
	 * @param upperBound 较大的边界
	 * @return 搜索到的位置
	 */
	private int recFind(long searchKey, int lowerBound, int upperBound) {
		int curIn;
		curIn=(lowerBound+upperBound)/2;
		//如果curIn的值等于searchKey，则直接返回
		if(a[curIn]==searchKey) {
			return curIn;
		}else if(lowerBound>upperBound) {
			return nElems;//找不到
		}else {
			if(a[curIn]<searchKey) {
				return recFind(searchKey, curIn+1, upperBound);
			}else {
				return recFind(searchKey, lowerBound, curIn-1);
			}
		}
		
	}
	
	public void insert(long value) {
		int j;
		for (j = 0; j < nElems; j++) {
			if(a[j]>value) {
				break;
			}
			for(int k=nElems;k>j;k--) {
				a[k]=a[k-1];
			}
			a[j]=value;
			nElems++;
		}
	}
	
	public void display() {
		for (int i = 0; i < nElems; i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
}
