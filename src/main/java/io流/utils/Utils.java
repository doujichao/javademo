package io流.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

	public static void copyDir(File src,File dest) {
		dest.mkdirs();
		if(src.isDirectory()) {
			File dir=new File(dest,src.getName());
			src.mkdirs();
			File[] files = src.listFiles();
			for (File file : files) {
				copyDir(file, dir);
			}
		}else {
			copyFile(src, dest);
		}
	}
	
	public static void copyFile(File srcFile,File destFile) {
		FileInputStream fis=null;
		FileOutputStream fos=null;
		try {
			fis=new FileInputStream(srcFile);
			fos=new FileOutputStream(new File(destFile,srcFile.getName()));
			int len=-1;
			byte[] arr=new byte[1024];
			while((len=fis.read(arr))!=-1) {
				fos.write(arr, 0, len);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
