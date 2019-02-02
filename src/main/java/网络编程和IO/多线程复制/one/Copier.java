package 网络编程和IO.多线程复制.one;

import java.io.RandomAccessFile;

/**
 * 复制器
 * @author douji
 *
 */
public class Copier {
	
	private MainUI copyUI;
	//源文件
	private String srcFile;
	//目标目录
	private String destDir;
	//线程数
	private int count;
	
	public Copier(MainUI mainUI,String srcFile, String destDir, int count) {
		copyUI=mainUI;
		this.srcFile = srcFile;
		this.destDir = destDir;
		this.count = count;
	}
	
	public void startCopy() throws Exception {
		int start=0;
		int end=0;
		
		//计算源文件大小
		RandomAccessFile r=new RandomAccessFile(srcFile, "r");
		int length = (int) r.length();
		copyUI.b.setMaximum(length);
		//每个线程复制的块大小
		int block=length/count;
		for(int i=0;i<count;i++) {
			start=i*block;
			if(i!=(count-1)) {
				end=(i+1)*block-1;
			}else {
				end=length-1;
			}
			
			new CopyThread(copyUI,srcFile,destDir,start,end).start();
		}
		r.close();
	}
	
	
	
}
