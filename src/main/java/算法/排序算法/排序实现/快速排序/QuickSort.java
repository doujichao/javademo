package 算法.排序算法.排序实现.快速排序;


import java.util.Arrays;

import org.junit.Test;

import 算法.排序算法.排序实现.Sort;

public class QuickSort extends Sort{

	@Override
	public void doSort(int[] ins) {
		quickSort(ins,0,ins.length-1);
	}
	/**
	 * 快速排序的主算法，和归并排序分组很相似，对对象进行迭代分组
	 * @param arr 待排序数组
	 * @param left 左边界
	 * @param right 右边界
	 */
	void quickSort(int[] arr,int left,int right) {
		//分组的返回条件，左右边界相等或者左小右大
		if(right-left<=0) {
			return ;
		}else {
			/**
			 * 在分组之前，先对数组进行划分，划分之后的数组，满足左边的元素比中间值小，右边的元素比中间值
			 * 大，这样之后再进行分组，对左右两部分分别进行划分，当满足左边界等于或者大于右边界的时候，数组
			 * 就已经基本有序了
			 */
			int partition=partitionIt(arr,left,right);//找出分组的中间值
			quickSort(arr, left, partition-1);//左边一只
			quickSort(arr, partition+1, right);//右边一只
		}
	}

	/**
	 * 划分算法
	 * @param arr 待划分数组
	 * @param left 左边界
	 * @param right 右边界
	 * @return 返回分界值下标
	 */
	private int partitionIt(int[] arr, int left, int right) {
		//目前没有分界值，先以中间值作为分界值
		int mid=(left+right)/2;
		/**
		 * 先把中间值取出来存到临时变量mm中，因为后续需要进行改变数组，所以
		 *我们需要先将作为临时中间值的变量用变量存起来
		 */
		int mm=arr[mid];
		//用leftPtr作为左操作数
		int leftPtr=left-1;
		//用rightPtr作为右操作数
		int rightPtr=right+1;
		while(true) {
			//找出左边比临时中间值大的数据下标
			while(leftPtr<right&&arr[++leftPtr]<mm);
			//找出右边比临时中间值大的数据下标
			while(rightPtr>left&&arr[--rightPtr]>mm);
			//如果左操作数比右操作数大，结束循环
			if(leftPtr>=rightPtr) 
				break;
			else
				//交换两个数据的
				doChange(arr, leftPtr, rightPtr);
		}
		return leftPtr;
	}
	/**
	 * 快速排序的主算法，和归并排序分组很相似，对对象进行迭代分组
	 * @param arr 待排序数组
	 * @param left 左边界
	 * @param right 右边界
	 */
	void quickSort1(int[] arr,int left,int right) {
		//分组的返回条件，左右边界相等或者左小右大
		if(right-left<=0) {
			return ;
		}else {
			int pivot=arr[right];
			/**
			 * 在分组之前，先对数组进行划分，划分之后的数组，满足左边的元素比中间值小，右边的元素比中间值
			 * 大，这样之后再进行分组，对左右两部分分别进行划分，当满足左边界等于或者大于右边界的时候，数组
			 * 就已经基本有序了
			 */
			int partition=partitionIt1(arr,left,right,pivot);//找出分组的中间值
			quickSort(arr, left, partition-1);//左边一只
			quickSort(arr, partition+1, right);//右边一只
		}
	}
	
	/**
	 * 划分算法
	 * @param arr 待划分数组
	 * @param left 左边界
	 * @param right 右边界
	 * @return 返回分界值下标
	 */
	private int partitionIt1(int[] arr, int left, int right,int pivot) {
		//用leftPtr作为左操作数
		int leftPtr=left-1;
		//用rightPtr作为右操作数
		int rightPtr=right;
		while(true) {
			//找出左边比临时中间值大的数据下标
			while(leftPtr<right&&arr[++leftPtr]<pivot);
			//找出右边比临时中间值大的数据下标
			while(rightPtr>left&&arr[--rightPtr]>pivot);
			//如果左操作数比右操作数大，结束循环
			if(leftPtr>=rightPtr) 
				break;
			else
				//交换两个数据的
				doChange(arr, leftPtr, rightPtr);
		}
		doChange(arr, leftPtr, right);
		return leftPtr;
	}

	@Override
	public void doSort1(int[] ins) {
		quickSort1(ins,0,ins.length-1);
	}
	
	@Test
	public void test() {
		int[] ins={24,6,4,12,35,33,67,9,7,7,6,43,0,0};
		doSort(ins);
		System.out.println(Arrays.toString(ins));
	}
}
