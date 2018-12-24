package io流.bytearray;

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
