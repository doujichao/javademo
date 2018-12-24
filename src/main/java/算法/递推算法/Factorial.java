package 算法.递推算法;

public class Factorial {

	public static int doFactorial(int i) {
		return i<=1?1:(i*doFactorial(i-1));
	}
}
