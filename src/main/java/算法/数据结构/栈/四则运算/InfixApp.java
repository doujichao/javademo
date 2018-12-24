package 算法.数据结构.栈.四则运算;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class InfixApp{
	@Test
	public void test() {
		String input,output;
		while(true) {
			System.out.println("Enter infix:");
			System.out.flush();
			input=getString();
			if(input.equals("")) {
				break;
			}
			InToPost theTrans = new InToPost(input);
			output  = theTrans.doTrans();
			System.out.println("Postfix is "+output);
		}
	}

	private static String getString()  {
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		String s=null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
