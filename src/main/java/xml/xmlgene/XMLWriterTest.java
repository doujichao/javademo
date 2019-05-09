package xml.xmlgene;

import javax.swing.*;
import java.awt.*;

/**
 * this program show how to write an XMl file,it saves a file describing a modern
 * drawing in SVG format
 */
public class XMLWriterTest {

    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            JFrame frame=new XMLWriterFrame();
            frame.setTitle("XMLWriterTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
