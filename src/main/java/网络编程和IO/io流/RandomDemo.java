package 网络编程和IO.io流;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 随机访问文件，支持随机读写
 * 1、seek()
 * 		绝对的量
 * 2、skip()
 * 		相对值
 * 3、read
 * 4、write
 * 5、RandomAccessFile(String str,String mode)
 * 		r:
 * 		rw:
 * 		rws:
 * 		rwd:
 * 6、文件复制
 * @author douji
 *
 */
public class RandomDemo {
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf=new RandomAccessFile("d:\\1\\3.txt", "rw"); 
		raf.write("hello world".getBytes());
		int c=-1;
		raf.seek(3);
		
		while((c=raf.read())!=-1) {
			System.out.print((char)c);
		}
		raf.close();
		
	}
	
}
