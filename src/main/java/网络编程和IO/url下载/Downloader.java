package 网络编程和IO.url下载;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Downloader {
    /**
     * 文件url
     */
    private String url;
    /**
     * 本地存放地址
     */
    private String local;
    /**
     * 线程个数
     */
    private int count;
    /**
     * 每个线程信息
     */
    private List<DownloadInfo> infos;
    /**
     * 文件长度
     */
    private int fileLength;

    private DownloadUI ui;
    private File metaFile;
    private Properties prop;

    public Downloader(String url, String local, int count,DownloadUI ui) {
        this.url = url;
        this.ui=ui;
        this.local = local;
        this.count = count;
        prepareDownload();
    }
    /**
     * 准备下载
     */
    private void prepareDownload() {
        try {
            //下载文件
            File localFile=new File(local);

            String fileName=local.substring(local.lastIndexOf("/")+1,local.lastIndexOf("."));
            //0、判断是新传还是旧传
            metaFile = new File(localFile.getParent(),fileName+".properties");
            //续传
            if(metaFile.exists()){
                processOldDownloadInfos(metaFile);
            }
            //新传
            else{
                //1、获取服务器文件信息
                URL u=new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                fileLength = conn.getContentLength();
                //2、创建本地文件并且设置大小
                RandomAccessFile raf=new RandomAccessFile(local,"rw");
                raf.setLength(fileLength);
                raf.close();
                //3、计算下载集合
                initDownloadInfo();

                //4、存储下载源文件
                saveDownloadMetaInfo(metaFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存下载信息元数据
     * @param metaFile
     */
    private void saveDownloadMetaInfo(File metaFile) {
        try {
            Properties prop=new Properties();
            prop.setProperty("url",url);
            prop.setProperty("thread.count",count+"");
            for(DownloadInfo info:infos){
                prop.setProperty("thread."+info.getIndex()+".url",url);
                prop.setProperty("thread."+info.getIndex()+".local",local);
                prop.setProperty("thread."+info.getIndex()+".start",info.getStart()+"");
                prop.setProperty("thread."+info.getIndex()+".end",info.getEnd()+"");
                prop.setProperty("thread."+info.getIndex()+".amount",info.getAmount()+"");

            }
            prop.store(new FileOutputStream(metaFile),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取上次的下载线程信息
     * @param metaFile
     */
    private void processOldDownloadInfos(File metaFile) {
        try {
            infos=new ArrayList<DownloadInfo>();
            Properties prop=new Properties();
            prop.load(new FileInputStream(metaFile));
            //获取线程个数
            this.count = Integer.parseInt(prop.getProperty("thread.count"));
            this.url=prop.getProperty("url");
            DownloadInfo info;
            for (int i=0;i<count;i++){
                info=new DownloadInfo();
                info.setUrl(url);
                info.setLocal(local);
                info.setIndex(i);
                info.setMetaFile(metaFile);
                info.setStart(Integer.parseInt(prop.getProperty("thread."+i+".start")));
                info.setEnd(Integer.parseInt(prop.getProperty("thread."+i+".end")));
                info.setAmount(Integer.parseInt(prop.getProperty("thread."+i+".amount")));
                infos.add(info);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算下载信息的集合
     */
    private void initDownloadInfo() {
        infos=new ArrayList<DownloadInfo>();
        DownloadInfo info;
        //每个线程下载的块数
        int block=fileLength / count;
        for(int i=0;i<count;i++){
            info=new DownloadInfo();
            info.setUrl(url);
            info.setLocal(local);
            info.setIndex(i);
            info.setMetaFile(metaFile);

            //start
            info.setStart(i * block);
            if(i != count-1){
                info.setEnd((i+1)*block-1);
            }else {
                info.setEnd(fileLength-1);
            }
            infos.add(info);
        }
    }

    public List<DownloadInfo> getInfos() {
        return infos;
    }

    public void startDownload() {
        try {
            prop = new Properties();
            prop.load(new FileInputStream(metaFile));
            for(DownloadInfo info:infos){
                new DownloadThread(info,ui, prop).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将下载信息元数据保存到文件
     */
    public void saveMetalInfo2File(){
        try {
            prop.store(new FileOutputStream(metaFile),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
