package io流.multhread;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CopyBlock extends Thread{
	
	private RandomAccessFile srcRaf;
	private RandomAccessFile destRaf;
	private int start;
	private int end;
	
	public CopyBlock(RandomAccessFile srcFile,RandomAccessFile destFile,int start,int end,String name) {
		super(name);
		this.srcRaf=srcFile;
		this.destRaf=destFile;
		this.start=start;
		this.end=end;
	}

	public void run() {
		
		try {
			//定位文件指针
			srcRaf.seek(start);
			destRaf.seek(start);
			int bufSize=end - start + 1 ;
			byte[] buf=new byte[bufSize];
			srcRaf.read(buf);
			destRaf.write(buf);
			System.out.println(getName()+": over");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}; 
}
