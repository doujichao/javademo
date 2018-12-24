package io流.multhread;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MulThreadCopyFile {
	

	public static void main(String[] args) throws IOException, Exception {
		//源文件
		File srcFile=new File("E:\\工作文档.zip");
		RandomAccessFile srcRaf=new RandomAccessFile(srcFile, "r");
		int srcLength=(int) srcFile.length();
		
		File destFile=new File("d:/你好.zip");
		
		final RandomAccessFile destRaf=new RandomAccessFile(destFile, "rw");
		
		//使用的线程数
		int count=3;
		int block;
		//计算每个线程复制的文件块大小
		block=srcLength/count;
		
		
		int start,end;
		//开启线程count个线程
		for(int i=0;i<count;i++) {
			start=i*block;
			end=(i==count)?((i+1)*block-1):(srcLength-1);
			Thread t=new CopyBlock(srcRaf, destRaf, start, end, i+"");
			t.start();
			Thread.sleep(100);
		}
		System.out.println("over");
	}
}
