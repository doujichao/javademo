package 算法.排序算法.排序实现;

public abstract class Sort implements 算法.排序算法.Sort {

	@Override
	public void doSort(int[] ins) {
		// TODO Auto-generated method stub

	}
	
	protected void doChange(int[] arrs,int a,int b) {
		int temp=arrs[a];
		arrs[a]=arrs[b];
		arrs[b]=temp;
	}
}
