package 网络编程.多线程复制.two;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JProgressBar;

/**
 * 复制线程
 * @author douji
 *
 */
public class CopyThread extends Thread{

	private MainUI copyUI;
	private CopyInfo copyInfo;
	
	public CopyThread(MainUI mainUI,CopyInfo copyInfo) {
		copyUI=mainUI;
		this.copyInfo=copyInfo;
	}

	@Override
	public void run() {
		int index = copyInfo.getIndex();
		JProgressBar pp=new JProgressBar();
		pp.setBounds(10,230+index*30,650,20);
		pp.setMaximum(copyInfo.getEnd()-copyInfo.getStart()+1);
		copyUI.add(pp);
		copyUI.repaint();
		try {
			//定位源文件
			RandomAccessFile src=new RandomAccessFile(copyInfo.getSrcFile(), "r");
			src.seek(copyInfo.getStart());
			//定位目标文件
			RandomAccessFile dest=new RandomAccessFile(copyInfo.getDestDir(), "rw");
			dest.seek(copyInfo.getStart());
			byte[] buffer=new byte[1024];
			int amount=copyInfo.getEnd()-copyInfo.getStart()+1;
			int loopNum=amount / buffer.length;
			int remain = amount % buffer.length;
			for(int i=0;i<loopNum;i++) {
				src.read(buffer);
				dest.write(buffer);
				pp.setValue(pp.getValue()+buffer.length);
			}
			if(remain!=0) {
				byte[] buffer0=new byte[remain];
				src.read(buffer0);
				dest.write(buffer0);
				pp.setValue(pp.getValue()+buffer0.length);
				
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pp.getValue()==pp.getMaximum()) {
				copyUI.remove(pp);
				copyUI.repaint();
			}
			src.close();
			dest.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
