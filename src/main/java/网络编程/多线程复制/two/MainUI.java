package 网络编程.多线程复制.two;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainUI extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 86750344831059086L;
	private JLabel lblSrcFile;
    private JTextField tfSrcFile;
    private JLabel lblDestFile;
    private JTextField tfDestFile;
    private JLabel lbCount;
    private JTextField tfCount;
    private JButton btnStart;
    private JFileChooser chooser;
    private JButton choBtn;
    
    public MainUI(){
        init();

    }

    private void init() {
        this.setTitle("主窗口");
        this.setBounds(100,100,800,600);
        this.setLayout(null);

        //
        tfSrcFile = new JTextField();
        tfSrcFile.setText("F:\\6.C算法.zip");
        tfSrcFile.setBounds(60,10,400,30);
        this.add(tfSrcFile);

        //srcFile标签
        lblSrcFile = new JLabel("源文件");
        lblSrcFile.setBounds(10,10,50,30);
        this.add(lblSrcFile);

        choBtn=new JButton("打开文件");
        choBtn.setBounds(460, 10, 100, 30);
        choBtn.addActionListener(this);
        this.add(choBtn);

        //
        tfDestFile = new JTextField();
        tfDestFile.setText("d:/1/和覅.zip");
        tfDestFile.setBounds(60,80,400,30);
        this.add(tfDestFile);

        //destFile标签
        lblDestFile = new JLabel("目标目录");
        lblDestFile.setBounds(10,80,80,30);
        this.add(lblDestFile);

        //文本荣誉
        tfCount = new JTextField();
        tfCount.setBounds(60,150,400,30);
        this.add(tfCount);

        //线程数
        lbCount = new JLabel("线程数");
        lbCount.setBounds(10,150,80,30);
        this.add(lbCount);

        //开始按钮
        btnStart=new JButton("开始");
        btnStart.setBounds(10,200,100,30);
        this.add(btnStart);
        btnStart.addActionListener(this);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        if(source==btnStart){
        	String srcFile = tfSrcFile.getText();
        	String destDir = tfDestFile.getText();
        	String text = tfCount.getText();
        	int count = Integer.parseInt(text);
        	
        	//创建复制器对象
        	Copier copier=new Copier(this,srcFile, destDir, count);
			
        	//开始复制
        	copier.startCopy();
        }else if(source==choBtn){
        	chooser=new JFileChooser();
        	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        	int ret=chooser.showOpenDialog(tfSrcFile);
        	if(ret==JFileChooser.APPROVE_OPTION) {
        		File file = chooser.getSelectedFile();
        		tfSrcFile.setText(file.getAbsolutePath());
        	}
        }
    }

}
