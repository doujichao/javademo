package 网络编程和IO.多线程复制.two;

/**
 * 复制信息
 * @author douji
 *
 */
public class CopyInfo {

	private int index;
	private String srcFile;
	private String destDir;
	private int start;
	private int end;
	public String getSrcFile() {
		return srcFile;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	public String getDestDir() {
		return destDir;
	}
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}
