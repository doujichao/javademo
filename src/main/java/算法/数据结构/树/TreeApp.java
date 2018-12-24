package 算法.数据结构.树;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class TreeApp {

	public static void main(String[] args) {
		int value;
		Tree theTree=new Tree();
		theTree.insert(50, 1.5);
		theTree.insert(25, 1.2);
		theTree.insert(75, 1.7);
		theTree.insert(12, 1.5);
		theTree.insert(37, 1.2);
		theTree.insert(43, 1.7);
		theTree.insert(30, 1.5);
		theTree.insert(33, 1.2);
		theTree.insert(87, 1.7);
		theTree.insert(93, 1.5);
		theTree.insert(97, 1.5);

		while(true) {
			System.out.print("Enter first letter of show, ");
			System.out.print("insert , find , delete ,or traverse:");
			int choice=getChar();
			switch(choice) {
			case 's':
				theTree.displayTree();
				break;
			case 'i' :
				System.out.print("Enter value to insert:");
				value=getInt();
				theTree.insert(value, value+0.9);
				break;
			case 'f':
				System.out.print("Enter value to find:");
				value=getInt();
				Node found=theTree.find(value);
				if(found!=null) {
					System.out.print("Found:");
					found.display();
					System.out.println();
				}else {
					System.out.print("Could not find");
					System.out.println();break;
				}
			case 'd':
				System.out.println("Enter value to delete:");
				value = getInt();
				boolean didDelete=theTree.delete(value);
				if(didDelete) {
					System.out.print("Deleted"+value+"\n");
				}else {
					System.out.println("Could not delete");
					System.out.println(value+'\n');
				}
				
			case 't':
				System.out.println("Enter type 1,2 or 3");
				value=getInt();
				theTree.traverse(value);
				break;
				default:System.out.println("Invalid entry");
			}
		}
	}

	private static String getString() {
		return getString1();
	}

	public static String getString1() {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static int getInt() {
		String s = getString();
		return Integer.parseInt(s);
	}

	private static int getChar() {
		String s = getString();
		return s.charAt(0);
	}
	@Test
	public void test() {
		int[][] arr= {{1,2,3},{2,3,4},{2,3,4}};
		System.out.println(arr.length);
		System.out.println(arr[0].length);
	}
}
