package 网络编程.多线程复制.two;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

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
	
	//每个线程复制信息的集合
	private List<CopyInfo> infos;
	
	public Copier(MainUI mainUI,String srcFile, String destDir, int count) {
		copyUI=mainUI;
		this.srcFile = srcFile;
		this.destDir = destDir;
		this.count = count;

		initCopyInfos();
	}
	
	/**
	 * 初始化
	 */
	private void initCopyInfos() {
		try {
			infos=new ArrayList<CopyInfo>();
		
			
			//计算源文件大小
			RandomAccessFile r=new RandomAccessFile(srcFile, "r");
			int length = (int) r.length();
			//每个线程复制的块大小
			int block=length/count;
			int start;
			int end;
			for(int i=0;i<count;i++) {
				CopyInfo info=new CopyInfo();
				info.setSrcFile(srcFile);
				info.setDestDir(destDir);
				info.setIndex(i);
				start=i*block;
				info.setStart(start);
				
				if(i!=(count-1)) {
					end=(i+1)*block-1;
				}else {
					end=length-1;
				}
				
				info.setEnd(end);
				infos.add(info);
			}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startCopy()  {
		for (CopyInfo info : infos) {
			new CopyThread(copyUI, info).start();
		}
	}
	
	
	
}
