package 算法.test;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import 算法.utils.Utils;
import 算法.排序算法.Sort;
import 算法.排序算法.排序实现.BubbleSort;
import 算法.排序算法.排序实现.InsertionSort;
import 算法.排序算法.排序实现.MergerSort;
import 算法.排序算法.排序实现.SelectionSort;
import 算法.排序算法.排序实现.shell.ShellSort;
import 算法.排序算法.排序实现.快速排序.QuickSort;

public class SortDemo {
	
	static Sort[] ss=new Sort[] {new BubbleSort(),new SelectionSort(),
			new InsertionSort(),new ShellSort(),new QuickSort(),new MergerSort()};

	static Random r=new Random(100);
	
	public static void main(String[] args) {
		int len = Utils.getInt();
		while(true) {
			
			int[] bys=new int[len];
			for(int i=0;i<len;i++) {
				bys[i]=r.nextInt(len);
			}
			
			int i = Utils.getInt();
			if(i>=ss.length) break;
			Sort s=ss[i];
			
			
			long begin = System.currentTimeMillis();
			s.doSort(bys);
			long end = System.currentTimeMillis();
			System.out.println(Arrays.toString(bys));
			System.out.println("使用时间为："+(end-begin));
		}
	}
	
	@Test
	public void test() {
		int len = Utils.getInt();
		int j=0;
		while(++j<=5) {
			
			int[] bys=new int[len];
			for(int i=0;i<len;i++) {
				bys[i]=r.nextInt(len);
			}
			
			Sort s=ss[4];
			
			
			long begin = System.currentTimeMillis();
			s.doSort(bys);
			long end = System.currentTimeMillis();
//			System.out.println(Arrays.toString(bys));
			/*long begin1 = System.currentTimeMillis();
			s.noDups(bys);
			long end2 = System.currentTimeMillis();
			System.out.println(Arrays.toString(bys));*/
			System.out.println("使用时间为："+(end-begin));
//			System.out.println("使用时间为："+(end2-begin1));
		}
	}
}
