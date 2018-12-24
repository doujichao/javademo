package 算法.排序算法.排序实现.对象排序;

public class Person {

	private String lastName;
	private String firstName;
	private int age;
	public Person(String lastName, String firstName, int age) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
	}
	public void displayPerson() {
		System.out.print("LastName:"+lastName);
		System.out.print(", FirstName:"+firstName);
		System.out.println(", Age:"+age);
	}
	public String getLast() {
		return lastName;
	}
}

class ArrayInOb{
	private Person[] a;
	private int nElems;
	public ArrayInOb(int max) {
		a=new Person[max];
		nElems=0;
	}
	public void insert (String last,String first,int age) {
		a[nElems]=new Person(last,first,age);
		nElems++;
	}
	
	public void display() {
		for (int i = 0; i < nElems; i++) {
			a[i].displayPerson();
		}
		System.out.println();
	}
	
	public void insertionSort() {
		int in,out;
		for (out = 0; out < nElems;out++) {
			Person temp=a[out];
			in=out;
			while(in>0&&a[in-1].getLast().compareTo(temp.getLast())>0) {
				a[in]=a[in-1];
				in--;
			}
			a[in++]=temp;
		}
	}
}
