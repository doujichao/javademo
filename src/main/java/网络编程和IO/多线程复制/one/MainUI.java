package 网络编程和IO.多线程复制.one;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
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
	public JProgressBar b;

    public MainUI(){
        init();

    }

    private void init() {
        this.setTitle("主窗口");
        this.setBounds(100,100,800,600);
        this.setLayout(null);
        //srcFile标签
        lblSrcFile = new JLabel("URL:");
        lblSrcFile.setBounds(10,10,50,30);
        this.add(lblSrcFile);

        //
        tfSrcFile = new JTextField();
        tfSrcFile.setText("F:\\6.C算法.zip");
        tfSrcFile.setBounds(60,10,400,30);
        this.add(tfSrcFile);


        //destFile标签
        lblDestFile = new JLabel("目标目录");
        lblDestFile.setBounds(10,80,80,30);
        this.add(lblDestFile);

        //
        tfDestFile = new JTextField();
        tfDestFile.setText("d:/1/和覅.zip");
        tfDestFile.setBounds(60,80,400,30);
        this.add(tfDestFile);

        //线程数
        lbCount = new JLabel("线程数");
        lbCount.setBounds(10,150,80,30);
        this.add(lbCount);

        //
        tfCount = new JTextField();
        tfCount.setBounds(60,150,400,30);
        this.add(tfCount);

        //开始按钮
        btnStart=new JButton("开始");
        btnStart.setBounds(10,200,100,30);
        this.add(btnStart);
        btnStart.addActionListener(this);

        b = new JProgressBar();
        b.setBounds(10,230,600,50);

        this.add(b);

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
        	try {
				copier.startCopy();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
}
