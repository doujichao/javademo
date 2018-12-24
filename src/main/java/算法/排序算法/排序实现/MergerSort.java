package 算法.排序算法.排序实现;


public class MergerSort extends Sort{
	
	private int[] theArray;

	@Override
	public void doSort(int[] ins) {
		int length = ins.length;
		theArray=ins;
		int[] workSpace=new int[length+10];
		recMergeSort(workSpace,0,length-1);
		
	}

	/**
	 * 对数组进行分组，这一步是通过对数组进行分组，直到数组所分组的大小为1时，进行归并
	 * @param workSpace 工作数组
	 * @param lowerBound 数组较小下标
	 * @param upperBound 数组较大下标
	 */
	private void recMergeSort(int[] workSpace, int lowerBound, int upperBound) {
		if(lowerBound==upperBound){
			return;
		}else {
			int mid=(lowerBound+upperBound)/2;
			/**
			 * 对左边的数组进行再分组，因为此时的数组大小还是大于1
			 */
			recMergeSort(workSpace,lowerBound,mid);
			/**
			 * 对于右边的数组进行在分组
			 */
			recMergeSort(workSpace,mid+1,upperBound);
			merge(workSpace,lowerBound,mid+1,upperBound);
		}
	}

	/**
	 * 排序算法，将连个数组进行归并
	 * @param workSpace 工作数组
	 * @param first 要比较数组的最小下标
	 * @param second 比较数组的中间值
	 * @param end 要比较数组的最大下标
	 */
	private void merge(int[] workSpace, int first, int second, int end) {
		
		int begin=first,b=first;
		int mid=second-1;
		int n=end-begin+1;
		/**
		 * 当下标满足条件的时候，比较两列数组的首元素，将较小的元素安排进工作数组
		 */
		while(first<=mid&&second<=end) {
			if(theArray[first]<theArray[second]) {
				workSpace[begin++]=theArray[first++];
			}else {
				workSpace[begin++]=theArray[second++];
			}
		}
		/**
		 * 将左边数组中的多余元素排放进数组
		 */
		while(first<=mid) {
			workSpace[begin++]=theArray[first++];
		}
		/**
		 * 将右边数组中多余元素的排放进数组
		 */
		while(second<=end) {
			workSpace[begin++]=theArray[second++];
		}
		
		/**
		 * 这个需要注意，我们合并的是那一部分，就把那一部分复制到数组中
		 */
		for (int i = b; i < b+n; i++) {
			theArray[i]= workSpace[i];
		}
	}

	@Override
	public void doSort1(int[] ins) {
		// TODO Auto-generated method stub
		
	}
	
}

