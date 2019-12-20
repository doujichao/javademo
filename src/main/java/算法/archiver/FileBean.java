package 算法.archiver;

/**
 * 文件bean
 * @author douji
 *
 */
public class FileBean {

	private String fileName;
	private byte[] fileContent;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public FileBean(String fileName, byte[] fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	public FileBean() {
		super();
		// TODO Auto-generated constructor stub
	}
}
