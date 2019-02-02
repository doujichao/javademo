package 网络编程和IO.多线程复制.one;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 复制线程
 * @author douji
 *
 */
public class CopyThread extends Thread{

	private MainUI copyUI;
	private String srcFile;
	private String destDir;
	private int start;
	private int end;
	
	public CopyThread(MainUI mainUI,String srcFile, String destDir, int start, int end) {
		copyUI=mainUI;
		this.srcFile = srcFile;
		this.destDir = destDir;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		try {
			//定位源文件
			RandomAccessFile src=new RandomAccessFile(srcFile, "r");
			src.seek(start);
			//定位目标文件
			RandomAccessFile dest=new RandomAccessFile(destDir, "rw");
			dest.seek(start);
			byte[] buffer=new byte[1024];
			int amount=end-start+1;
			int loopNum=amount / buffer.length;
			int remain = amount % buffer.length;
			for(int i=0;i<loopNum;i++) {
				src.read(buffer);
				dest.write(buffer);
				synchronized(CopyThread.class) {
					copyUI.b.setValue(copyUI.b.getValue()+buffer.length);
				}
			}
			if(remain!=0) {
				byte[] buffer0=new byte[remain];
				src.read(buffer0);
				dest.write(buffer0);
				synchronized(CopyThread.class) {
					copyUI.b.setValue(copyUI.b.getValue()+buffer0.length);
				}
			}
			src.close();
			dest.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
