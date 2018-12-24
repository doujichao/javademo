package 算法.排序算法.排序实现;


/**
 * 选择排序：外指针从0开始，直到数组结束
 * 内指针从外指针开始向右遍历，遍历到数组结束，找到最小值，交换最小值和当前外指针所指元素，外指针加一
 * @author douji
 *
 */
public class SelectionSort extends Sort{

	@Override
	public void doSort(int[] ins) {
		
		for (int i = 0; i < ins.length; i++) {
			int index=i;
			for (int j = i; j < ins.length; j++) {
				if(ins[j]<ins[index]) {
					index=j;
				}
			}
			doChange(ins, i, index);
		}
	}

	@Override
	public void doSort1(int[] ins) {
		// TODO Auto-generated method stub
		
	}

}
