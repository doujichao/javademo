package xml.xmlgene;

import javax.swing.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.w3c.dom.*;

/**
 * A frame with a component for showing a modern drawing
 */
public class XMLWriterFrame extends JFrame {

    private RectangleComponent component;

    private JFileChooser chooser;

    public XMLWriterFrame (){
        chooser=new JFileChooser();

        //add component to frame
        component=new RectangleComponent();
        add(component);

        //set up menu bar
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu=new JMenu("File");
        menuBar.add(menu);

        JMenuItem newItem=new JMenuItem("New");
        menu.add(newItem);
        newItem.addActionListener(event->component.newDrawing());

        JMenuItem saveItem=new JMenuItem("Save with Dom/xslt");
        menu.add(saveItem);
        saveItem.addActionListener(event -> saveDocument());

        JMenuItem saveStAxItem=new JMenuItem("Save with Dom/xslt");
        menu.add(saveStAxItem);
        saveStAxItem.addActionListener(event -> saveStAX());

        JMenuItem exitItem=new JMenuItem("Save with Dom/xslt");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));

        pack();
    }

    /**
     * saves the drawing in svg format,using StAX
     */
    private void saveStAX() {
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(Files.newOutputStream(file.toPath()));
            try{
                component.writeDocument(writer);
            }finally {
                writer.close();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * save the drawing in svg gormat ,using DOM/XSLT
     */
    private void saveDocument() {
        try{
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            File file = chooser.getSelectedFile();
            Document doc=component.buildDocument();
            final Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");
            t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"-//W3C//DTD SVG 20000802//EN");
            t.setOutputProperty(OutputKeys.INDENT,"yes");
            t.setOutputProperty(OutputKeys.METHOD,"xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
            t.transform(new DOMSource(doc),new StreamResult(Files.newOutputStream(file.toPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
