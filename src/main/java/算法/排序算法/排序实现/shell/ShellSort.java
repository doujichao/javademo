package 算法.排序算法.排序实现.shell;

import java.util.Arrays;

import org.junit.Test;

import 算法.排序算法.排序实现.Sort;

public class ShellSort extends Sort {

	@Override
	public void doSort(int[] ins) {
		//以数组的长度的一半作为间隔，每次间隔都缩短一半，直到间隔变成1为止
		for (int r = ins.length/2; r >=1; r/=2) {
			/**
			 * 这里是因为插入排序是从下标为1的位置开始进行排序
			 * 所以这里从下标为r处开始遍历，
			 * 外指针从r开始每次增加1；直到外指针到达数组末尾
			 * 内指针从外指针开始，判断是否temp是否比当前元素小，
			 * 		满足：将该元素后置，内指针j继续向前
			 * 		不满足，将temp插入到内指针当前位置
			 */
			for(int i=r;i<ins.length;i++) {
				int temp=ins[i];
				int j=i-r;
				while(j>=0&&temp<ins[j]) {
					ins[j+r]=ins[j];
					j-=r;
				}
				ins[j+r]=temp;
			}
		}
	}
	
	@Override
	public void doSort1(int[] ins) {
		int h=ins.length/2;
		for(;h>=1;h/=2) {
			for(int i=0;i<h;i++) {
				for(int j=i+h;j<ins.length;j+=h) {
					int temp=ins[j];
					j-=h;
					while(j>=0&&temp<ins[j]) {
						ins[j+h]=ins[j];
						j-=h;
					}
					j+=h;
					ins[j]=temp;
				}
			}
		}
	}
	@Test
	public void test() {
		int[] ins={24,6,4,12,35,33,67,9,7,7,6,43,0,0};
		doSort1(ins);
		System.out.println(Arrays.toString(ins));
	}
}
