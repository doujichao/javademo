package 算法.递推算法.test;

public class XX列 {

	public static void main(String[] args) {
		Model m=new Model();
		for(int i=0;i<5;i++) {
			m.display();
			m.grawOne();
		}
	}
}

class Model{
	private int gNum=8;
	private int xNum=1;
	
	public void grawOne() {
		xNum*=2;
		gNum/=2;
	}
	
	public void display() {
		if(gNum!=0) {
			for (int i = 0; i < xNum; i++) {
				displayOne();
			}
		}else {
			for(int i=0;i<17;i++) {
				System.out.print("X");
			}
		}
		System.out.println();
	}
	
	/**
	 *打印显示出一组
	 */
	public void displayOne() {
		for(int i=0;i<gNum;i++) {
			System.out.print("-");
		}
		System.out.print("X");
		for(int i=0;i<gNum-1;i++) {
			System.out.print("-");
		}
	}
	
}
