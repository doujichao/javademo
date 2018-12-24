package 网络编程.url下载;

import java.io.File;

public class DownloadInfo {
    /**
     * url地址
     */
    private String url;
    /**
     * 本地文件路径
     */
    private String local;

    //元数据文件
    private File metaFile;

    /**
     * 下载起始位置
     */
    private int start;
    /**
     * 下载结束位置
     */
    private int end;
    /**
     * 线程编号
     */
    private int index;
    private int amount=0;

    public File getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(File metaFile) {
        this.metaFile = metaFile;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
