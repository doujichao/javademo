package 算法.递推算法;

public class RabbitSum {

	public static int Fibonacci(int i) {
		int t1,t2;
		if(i==1||i==2) {
			return 1;
		}else {
			/**
			 * 通过先将结果赋值给一个变量，从而使得
			 * 我们可以将一个递归运算不断的分层，并且不用考虑
			 * 每层的分配结构，只需要不断分层即可
			 */
			t1=Fibonacci(i-1);
			t2=Fibonacci(i-2);
			return t1+t2;
		}
	}
	
	
}
