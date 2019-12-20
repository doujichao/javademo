package 算法.archiver;


import util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 归档类
 * @author douji
 *
 */
public class Archiver {

	public static void main(String[] args) throws Exception {
		FileOutputStream fos=new FileOutputStream("D:\\1.xar");
		fos.write(processFile("D:\\1\\1.jpg"));
		fos.write(processFile("D:\\1\\2.class"));
		fos.write(processFile("D:\\1\\3.txt"));
		fos.close();
	}
	
	/**
	 * 
	 * @param path 
	 * @return
	 * @throws IOException 
	 */
	public static byte[] processFile(String path) throws IOException {
		//文件
		File f = new File(path);
		//文件名
		String fname = f.getName();
		//文件名数组
		byte[] fnameBytes = fname.getBytes() ;
		//文件内容长度
		int len = (int)f.length();
		
		//计算总长度
		int total = 4 + fnameBytes.length + 4 + len ;
		
		//初始化总数组
		byte[] bytes = new byte[total];
		
		//1.写入文件名长度
		byte[] fnameLenArr = Util.int2Bytes(fnameBytes.length);
		System.arraycopy(fnameLenArr, 0, bytes, 0, 4);
		
		//2.写入文件名本身
		System.arraycopy(fnameBytes, 0, bytes, 4, fnameBytes.length);
		
		//3.写入文件内容长度
		byte[] fcontentLenArr = Util.int2Bytes(len);
		System.arraycopy(fcontentLenArr, 0, bytes, 4 + fnameBytes.length, 4);
		
		//4.写入文件内容
		//读取文件内容到数组中
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		int len0 = 0 ;
		while(((len0 = fis.read(buf)) != -1)){
			baos.write(buf, 0, len0);
		}
		fis.close();
		//得到文件内容
		byte[] fileContentArr = baos.toByteArray();
		
		System.arraycopy(fileContentArr, 0, bytes, 4 + fnameBytes.length + 4, fileContentArr.length);
		
		return bytes ;
	}
}
