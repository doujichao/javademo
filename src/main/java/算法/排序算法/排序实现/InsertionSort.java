package 算法.排序算法.排序实现;

import java.util.Arrays;

import org.junit.Test;

import 算法.排序算法.Sort;

/**
 * 插入排序：外侧指针从1开始遍历到数组结束
 * 内侧指针：从外侧指针开始，向左遍历找到最小值，存到临时变量中，遍历过程中，将元素向后复制，将最小值插入到外指针所指的位置
 * @author douji
 *
 */
public class InsertionSort implements Sort{

	@Override
	public void doSort(int[] ins) {
		int i ,j,len=ins.length,re=0,copy=0;
		for (i = 1; i < len; i++) {
			int temp=ins[i];
			j=i-1;
			/**
			 * 不能只熟悉for循环
			 */
			while(j>=0&&(temp<ins[j]&&re++>=0)) {
				ins[j+1]=ins[j];
				copy++;
				j--;
			}
			ins[j+1]=temp;
			copy++;
		}
		System.out.println("比较的次数："+re);
		System.out.println("复制的次数："+copy);
	}

	@Override
	public void doSort1(int[] ins) {

	}

	/**
	 * 去除重复元素
	 * @param ins 已经有序的数组
	 */
	public void noDups(int[] ins) {
		int flag=0;
		for (int i = 0; i < ins.length-1; ) {
			while(ins[i]==ins[i+1]&&++i<(ins.length-1) );
			ins[flag++]=ins[i++];
		}
		int len=ins.length-1;
		if(ins[len]!=ins[len-1]) {
			ins[flag++]=ins[len];
		}
		for(int i=flag;i<ins.length;i++) {
			ins[i]=0;
		}
	}
	
	@Test
	public void test() {
		int arr[]= {1,1,3,44,56,56,56,56,77,88,88,99};
		noDups(arr);
		System.out.println(Arrays.toString(arr));
	}

}
