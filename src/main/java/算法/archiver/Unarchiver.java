package 算法.archiver;



import util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;



/**
 * 解档程序
 * @author douji
 *
 */
public class Unarchiver {

	public static void main(String[] args) throws Exception {
		List<FileBean> files=new ArrayList<FileBean>();
		//
		File f=new File("d:\\1.xar") ;
		FileInputStream fis=new FileInputStream(f);
		//
		FileBean file=null;
		while((file=readNextFile(fis))!=null) {
			files.add(file);
		}
		fis.close();
		FileOutputStream fos=null;
		for (FileBean fb : files) {
			System.out.println(fb.getFileName());
			fos=new FileOutputStream("d:\\1"+fb.getFileName());
			fos.write(fb.getFileContent());
			fos.close();
		}
		
	}
	
	/**
	 * 从流中读取下一个文件
	 * @param fis
	 * @return
	 * @throws Exception 
	 */
	public static FileBean readNextFile(FileInputStream fis) throws Exception {
		//
		byte[] bytes4 = new byte[4];
		//读取四个字节
		int res = fis.read(bytes4);
		if(res == -1){
			return null ;
		}
		//文件名长度
		int fnameLen = Util.byte2Int(bytes4,0);
		
		//文件名数组
		byte[] fileNameBytes = new byte[fnameLen];
		fis.read(fileNameBytes);
		
		//得到文件名
		String fname = new String(fileNameBytes);
		
		//再读取4个字节，作为文件内容的长度
		fis.read(bytes4);
		int fileContLen = Util.byte2Int(bytes4,0);
		
		//读取文件内容
		byte[] fileContBytes = new byte[fileContLen];
		fis.read(fileContBytes);
		return new FileBean(fname,fileContBytes);
	}
}
