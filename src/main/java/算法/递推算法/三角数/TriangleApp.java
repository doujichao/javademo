package 算法.递推算法.三角数;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TriangleApp {

	static int theNumber;
	
	public static void main(String[] args) {
		System.out.println("Enter a number:");
		theNumber=getInt();
		int theAnswer=triangle(theNumber);
		System.out.println("Triangle="+theAnswer);
		
	}

	public static int triangle(int n) {
		if(n==1) {
			return 1;
		}else {
			return n+triangle(n-1);
		}
	}
	public static int factorial(int n) {
		if(n==0) {
			return 1;
		}else {
			return n*factorial(n-1);
		}
	}

	private static int getInt() {
		String s=null;
		try {
			s = getString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(s);
	}

	private static String getString() throws IOException {
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
}
