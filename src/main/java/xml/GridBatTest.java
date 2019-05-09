package xml;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * this program show how to use an XML file to describe a grid layout
 */
public class GridBatTest {

    public static void main(String[] args){
        JFileChooser chooser=new JFileChooser(".");
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        JFrame frame=new FontFrame(file);
        frame.setTitle("GridBagTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
 * this frame contains a font selection dialog that is described by an XML file
 */
class FontFrame extends JFrame {

    //面板
    private GridBagPane gridbagPane;
    private JComboBox<String> face;
    private JComboBox<String> size;
    private JCheckBox bold;
    private JCheckBox italic;

    public FontFrame(File file) {
        gridbagPane=new GridBagPane(file);
        add(gridbagPane);

        face= (JComboBox<String>) gridbagPane.get("face");
        size= (JComboBox<String>) gridbagPane.get("size");
        bold= (JCheckBox) gridbagPane.get("bold");
        italic= (JCheckBox) gridbagPane.get("italic");

        face.setModel(new DefaultComboBoxModel<String>(new String[]{
                "Serif","SansSerif","Monospaced","Dialog","DialogInput"
        }));

        size.setModel(new DefaultComboBoxModel<String>(new String[]{
                "10","12","15","18","24","36","48"
        }));

        ActionListener listener = e -> setSample();
        face.addActionListener(listener);
        size.addActionListener(listener);
        bold.addActionListener(listener);
        italic.addActionListener(listener);

        setSample();
        pack();
    }

    /**
     * this method sets the text sample to selected font
     */
    public void setSample() {
        String fontFace = face.getItemAt(face.getSelectedIndex());
        int fontSize = Integer.parseInt(size.getItemAt(size.getSelectedIndex()));
        JTextArea sample= (JTextArea) gridbagPane.get("sample");
        int fontStyle=(bold.isSelected() ? Font.BOLD : 0) + (italic.isSelected() ?
                Font.ITALIC:0);
        sample.setFont(new Font(fontFace,fontStyle,fontSize));
        sample.repaint();
    }
}