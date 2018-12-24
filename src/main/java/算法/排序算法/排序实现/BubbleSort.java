package 算法.排序算法.排序实现;


public class BubbleSort extends Sort{

	@Override
	public void doSort(int[] ins) {
		int out=ins.length;
		for (int i = 0; i < ins.length; i++) {
			out--;
			for (int j = 1; j <=out; j++) {
				if(ins[j-1]>ins[j]) {
					doChange(ins, j, j-1);
				}
			}
		}
	}
	
	public void doSort1(int[] ins) {
		int right=ins.length-1,left=0,in=0;
		while(right>left) {
			for(in =left;in<right;in++) {
				if(ins[in]>ins[in+1]) {
					doChange(ins, in, in+1);
				}
			}
			right--;
			for(in=right+1;in>left;in--) {
				if(ins[in]<ins[in-1]) {
					doChange(ins, in, in-1);
				}
			}
			left++;
		}
	}

}
