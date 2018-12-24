package 网络编程.screenbroadcast;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadCastScreenClientUI extends JFrame{

    private JLabel label;

    public BroadCastScreenClientUI(){
        init();
        refreshUI();
    }

    private void refreshUI() {
        new Thread(){
            @Override
            public void run() {
                try {
                    DatagramSocket socket=new DatagramSocket(9999);
                    while (true){
                        byte[] bys= new byte[1024*60];
                        DatagramPacket packet=new DatagramPacket(bys,bys.length);
                        socket.receive(packet);//接受数据包

                        int len = packet.getLength();
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bys, 0, len));
                        label.setIcon(new ImageIcon(image));

                        repaint();//重绘

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public  void init(){
        try {
            this.setSize(1300,800);
            this.setLayout(null);

            label = new JLabel();
            label.setBounds(0,0,300,300);

            BufferedImage image= ImageIO.read(new File("d:\\ss.jpg"));
            ImageIcon icon=new ImageIcon(image);
            label.setIcon(icon);
            this.add(label);

            this.setVisible(true);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(-1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
