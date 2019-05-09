package 网络编程和IO.io流.bytearray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * 串行化
 * @author douji
 *
 */
public class ObjectStreamDemo {

	public static void main(String[] args) {

		Employee harry=new Employee("Harry Hacker",5000,1989,10,1);
		Manager carl=new Manager("Carl Cracker",8000,1987,12,15);
		Manager toney=new Manager("Tony Tester",4000,1990,3,15);

		Employee[] staff=new Employee[3];
		staff[0]=carl;
		staff[1]=harry;
		staff[2]=toney;

		try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("employee.dat"))){
			oos.writeObject(staff);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream("employee.dat"))){
//			Employee o = (Employee) ois.readObject();
//			System.out.println(o);
//			Employee o1 = (Employee) ois.readObject();
//			System.out.println(o1);
			Employee[] newStaff= (Employee[]) ois.readObject();
			for (Employee e:newStaff){
				System.out.println(e);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void serialiable() throws Exception {
		Person p=new Person();
		p.setName("话还");
		p.setAge(1000);
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("d:/1/1.txt"));
		oos.writeObject(p);
		oos.close();
	}
	@Test
	public void deSerialiable() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("d:/1/1.txt"));
		Person p=(Person) ois.readObject();
		System.out.println(p.getName());
		System.out.println(p.getC().getName());
		ois.close();
	}
	@Test
	public void test() throws IOException {
		ByteArrayInputStream bais=new ByteArrayInputStream("好侯府怀".getBytes());
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		
		byte[] arr=new byte[1024];
		int read = bais.read(arr);
		baos.write(arr, 0, read);
		String string = baos.toString();
		System.out.println(string);
	}
	
	
}
