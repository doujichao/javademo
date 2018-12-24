package 网络编程.url下载;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class DownloadThread extends Thread{

    private DownloadInfo info;

    private DownloadUI ui;

    //所有线程共享的prop集合
    private Properties prop;

    public DownloadThread(DownloadInfo info,DownloadUI ui,Properties prop) {
        this.info=info;
        this.ui=ui;
        this.prop=prop;
    }



    @Override
    public void run() {
        try {
            URL url=new URL(info.getUrl());
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //设置请求头信息，这里是关键
            con.setRequestProperty("Range","bytes="+info.getStart()+"-"+info.getEnd());
            InputStream is = con.getInputStream();

            //本地文件
            RandomAccessFile raf=new RandomAccessFile(info.getLocal(),"rw");
            raf.seek(info.getStart() + info.getAmount());

            byte[] buffer=new byte[1024];
            int len;
            while((len=is.read(buffer))!=-1){

                while(true){
                    try {
                        if (ui.pausing){
                            Thread.sleep(200);
                        }else{
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                raf.write(buffer,0,len);
                //更新下载元数据信息
                updateMetaInfo(info.getIndex(),len);
                //进度条
                ui.updateBar(info.getIndex(),len);
            }
            raf.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新内存中的元数据值
     * @param index
     * @param len
     */
    private void updateMetaInfo(int index, int len) {
        int oldAmount=Integer.parseInt(prop.getProperty("thread."+index+".amount"));
        prop.setProperty("thread."+index+".amount",oldAmount+len+"");
    }
}
