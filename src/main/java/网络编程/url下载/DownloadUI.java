package 网络编程.url下载;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * 下载文件
 */
public class DownloadUI extends JFrame implements ActionListener {

    public boolean pausing=false;
    /**
     *
     */
    private JLabel lblSrcFile;
    private JTextField tfUrlFile;

    
    private JLabel lblLocalFile; 
    private JTextField tfLocalFile;

    
    private JLabel lbCount;
    private JTextField tfCount;


    private JButton btnStart;
    private JProgressBar[] bars;
    private JButton btnPause;
    private Downloader downloader;

    public DownloadUI(){
        init();

    }

    private void init() {
        this.setTitle("主窗口");
        this.setBounds(100,100,800,600);
        tfUrlFile = new JTextField();
        tfUrlFile.setText("http://localhost:8080/11.mp4");
        tfUrlFile.setBounds(60,10,400,30);
        this.add(tfUrlFile);
        this.setLayout(null);
        //srcFile标签
        lblSrcFile = new JLabel("连接地址");
        lblSrcFile.setBounds(10,10,50,30);
        this.add(lblSrcFile);
        //
        tfLocalFile = new JTextField();
        tfLocalFile.setText("d:/1/nihao.mp4");
        tfLocalFile.setBounds(60,80,400,30);
        this.add(tfLocalFile);


        //线程数
        lbCount = new JLabel("线程数");
        lbCount.setBounds(10,150,80,30);
        this.add(lbCount);

        //destFile标签
        lblLocalFile = new JLabel("目标文件");
        lblLocalFile.setBounds(10,80,80,30);
        this.add(lblLocalFile);

        //文本荣誉
        tfCount = new JTextField();
        tfCount.setBounds(60,150,400,30);
        this.add(tfCount);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        //开始按钮
        btnStart=new JButton("开始");
        btnStart.setBounds(10,200,100,30);
        this.add(btnStart);
        btnStart.addActionListener(this);


        //暂停按钮
        btnPause=new JButton("暂停");
        btnPause.setBounds(120,200,100,30);
        this.add(btnPause);
        btnPause.addActionListener(this);

        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                downloader.saveMetalInfo2File();
                System.exit(-1);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        if(source==btnStart){
            String url = tfUrlFile.getText();
            String local = tfLocalFile.getText();
            String count = tfCount.getText();
            //1、创建Downloader，传递数据
            downloader = new Downloader(url,local,Integer.parseInt(count),this);
            //2、取得infos
            List<DownloadInfo> infos = downloader.getInfos();

            //3、动态添加进度条
            this.addBars(infos);

            //4、开始下载
            downloader.startDownload();
        }//暂停
        else if(source==btnPause){
           pausing= !pausing;
        }
    }

    /**
     * 动态添加进度条
     * @param infos
     */
    private void addBars(List<DownloadInfo> infos) {
        bars=new JProgressBar[infos.size()];
        for(DownloadInfo info:infos){

            bars[info.getIndex()]=new JProgressBar();

            bars[info.getIndex()].setMaximum(info.getEnd()-info.getStart()+1);

            bars[info.getIndex()].setValue(info.getAmount());


            bars[info.getIndex()].setBounds(10,245+(info.getIndex()*50),800,50);
            this.add(bars[info.getIndex()]);
        }
        this.repaint();
    }

    /**
     * 更新进度条
     * @param index 进度条下标
     * @param len 长度
     */
    public void updateBar(int index, int len) {
        bars[index].setValue(bars[index].getValue()+len);
    }
}
