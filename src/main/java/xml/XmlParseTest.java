package xml;

import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class XmlParseTest {

    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            JFrame frame=new DOMTreeFrame();
            frame.setTitle("TreeView");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    @Test
    public void testDocumentParse() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder =
                factory.newDocumentBuilder();
        Document document = builder.parse(Paths.get("people.xml").toFile());
        Element root =
                document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i=0;i<childNodes.getLength();i++){
            Node item = childNodes.item(i);

        }
    }
}

/**
 * this frame contains a tree that display the contents of an Xml document
 */
class DOMTreeFrame extends JFrame{

    private static final int DEFAULT_WIDTH=800;
    private static final int DEFAULT_HEIGHT=800;

    private DocumentBuilder builder;

    public DOMTreeFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_WIDTH);
        //添加目录
        JMenu fileMenu=new JMenu("file");
        JMenuItem openItem=new JMenuItem("open");
        openItem.addActionListener(event->openFile());
        fileMenu.add(openItem);
        //添加退出
        JMenuItem exitItem=new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);

        JMenuBar menuBar=new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void openFile() {
         JFileChooser chooser=new JFileChooser();
         chooser.setCurrentDirectory(new File("dom"));
         chooser.setFileFilter(new FileNameExtensionFilter("XML files","xml"));
        int r = chooser.showOpenDialog(this);
        if (r!=JFileChooser.APPROVE_OPTION) return;

        final File file = chooser.getSelectedFile();
        new SwingWorker<Document,Void>(){

            @Override
            protected Document doInBackground() throws Exception {
                if (builder == null){
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    builder=factory.newDocumentBuilder();
                }
                return builder.parse(file);
            }

            @Override
            protected void done() {
                try{
                    Document doc = get();
                    JTree tree=new JTree(new DOMTreeModel(doc));
                    tree.setCellRenderer( new DOMTreeCellRenderer());

                    setContentPane(new JScrollPane(tree));
                    validate();
                } catch (Exception e) {
                   JOptionPane.showMessageDialog(DOMTreeFrame.this,e);
                }
            }
        }.execute();

    }
}

/**
 * This tree model describes tree structure of an XML
 *  document
 */
class DOMTreeModel implements TreeModel{

    private Document document;

    public DOMTreeModel(Document doc) {
        document=doc;
    }

    @Override
    public Object getRoot() {
        return document.getDocumentElement();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Node node= (Node) parent;
        NodeList list = node.getChildNodes();
        return list.item(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Node node= (Node) parent;
        NodeList list = node.getChildNodes();
        return list.getLength();
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node)==0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Node node= (Node) parent;
        NodeList list = node.getChildNodes();
        for (int i=0;i<list.getLength();i++){
            if (getChild(node,i)==child) return i;
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}

class DOMTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Node node= (Node) value;
        if (node instanceof Element) return elementPanel((Element)node);
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        if (node instanceof CharacterData) setText(characterString((CharacterData)node));
        else setText(node.getClass()+":"+node.toString());
        return this;
    }


    private String characterString(CharacterData node) {
        StringBuilder builder=new StringBuilder(node.getData());
        for (int i=0;i<builder.length();i++){
            if (builder.charAt(i) == '\r'){
                builder.replace(i,i+1,"\\r");
                i++;
            }else if (builder.charAt(i) == '\n'){
                builder.replace(i,i+1,"\\n");
                i++;
            }else if (builder.charAt(i) == '\t'){
                builder.replace(i,i+1,"\\t");
                i++;
            }
        }
        if (node instanceof  CDATASection) builder.insert(0,"CDATASection: ");
        else if (node instanceof Text) builder.insert(0,"Text: ");
        else if (node instanceof Comment) builder.insert(0,"Comment: ");
        return builder.toString();
    }

    public static JPanel elementPanel(Element node) {
        JPanel panel=new JPanel();
        panel.add(new JLabel("Element: "+ node.getTagName()));
        final NamedNodeMap map = node.getAttributes();
        panel.add(new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return map.getLength();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex==0?map.item(rowIndex):map.item(rowIndex).getNodeValue();
            }
        }));
        return panel;
    }
}