package xml;

import org.w3c.dom.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GridBagPane extends JPanel {

    private GridBagConstraints constraints;

    public GridBagPane(File file){
        setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();

        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            if (file.toString().contains("-schema")){
                factory.setNamespaceAware(true);
                final String JAXP_SCHEMA_LANGUAGE=
                        "http://java.sun.com/xml/jaxp/properties/schemaLauguage";
                final String W3C_XML_SCHEMA=
                        "http://www.w3.org/2001/XMLSchema";
                factory.setAttribute(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA);
            }
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            parseGridBag(doc.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * parses a gridBag element
     * @param root
     */
    private void parseGridBag(Element root) {
        NodeList rows = root.getChildNodes();
        for (int i=0;i<rows.getLength();i++){
            Element row= (Element) rows.item(i);
            NodeList cells = row.getChildNodes();
            for (int j=0;j<cells.getLength();j++){
                Element cell= (Element) cells.item(j);
                parseCell(cell,i,j);
            }
        }
    }

    /**
     * parses a cell element
     * @param cell a cell element
     * @param r the row of the cell
     * @param c the column of the cell
     */
    private void parseCell(Element cell, int r, int c) {
        String value = cell.getAttribute("gridx");
        if (value.length()==0){
            if (c==0){
                constraints.gridx=0;
            }else constraints.gridx+=constraints.gridwidth;
        }else constraints.gridx = Integer.parseInt(value);

        value = cell.getAttribute("gridy");
        if (value.length()==0){
            constraints.gridy=r;
        }else constraints.gridy = Integer.parseInt(value);

        constraints.gridwidth=Integer.parseInt(cell.getAttribute("gridwidth"));
        constraints.gridheight=Integer.parseInt(cell.getAttribute("gridheight"));
        constraints.weightx=Integer.parseInt(cell.getAttribute("weightx"));
        constraints.weighty=Integer.parseInt(cell.getAttribute("weighty"));
        constraints.ipadx=Integer.parseInt(cell.getAttribute("ipadx"));
        constraints.ipady=Integer.parseInt(cell.getAttribute("ipady"));

        Class<GridBagConstraints> cl=GridBagConstraints.class;

        try {
            String name = cell.getAttribute("fill");
            Field f = cl.getField(name);
            if (f.getModifiers() == Modifier.PRIVATE){
                f.setAccessible(true);
            }
            constraints.fill = f.getInt(cl);

            name = cell.getAttribute("anchor");
            f=cl.getField(name);
            if (f.getModifiers() == Modifier.PRIVATE){
                f.setAccessible(true);
            }
            constraints.anchor = f.getInt(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Component component=(Component)parseBean((Element)cell.getFirstChild());
        add(component,constraints);
    }

    /**
     * parse a bean element
     * @param e a bean element
     * @return
     */
    private Object parseBean(Element e) {
        try {
            NodeList children = e.getChildNodes();
            Node classElement = children.item(0);
            String className = ((Text) classElement.getFirstChild()).getData();
            Class<?> cl = Class.forName(className);
            Object object = cl.newInstance();
            if (object instanceof  Component)
                ((Component)object).setName(e.getAttribute("id"));

            for (int i=0;i<children.getLength();i++){
                Node propertyElement = children.item(i);
                Element nameElement = (Element) propertyElement.getFirstChild();
                String propertyName = ((Text) nameElement.getFirstChild()).getData();
                Element valueElement=(Element)propertyElement.getLastChild();
                Object value=parseValue(valueElement);
                BeanInfo beanInfo = Introspector.getBeanInfo(cl);
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                boolean done=false;
                for (int j=0;!done && j <descriptors.length;j++){
                    if (descriptors[j].getName().equals(propertyName)){
                        descriptors[j].getWriteMethod().invoke(object,value);
                        done=true;
                    }
                }
            }
            return object;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    private Object parseValue(Element e) {
        Element child= (Element) e.getFirstChild();
        if (child.getTagName().equals("bean")) return parseBean(child);
        String text=((Text)child.getFirstChild()).getData();
        if (child.getTagName().equals("int")) return new Integer(text);
        else if (child.getTagName().equals("boolean")) return new Boolean(text);
        else if (child.getTagName().equals("string"))return text;
        else return null;
    }

    /**
     * get a component with a given name
     * @param name a component name
     * @return the component with the given name,or null if no component in this grid
     * bag pane has the given name
     */
    public Component get(String name) {
        Component[] components = getComponents();
        for (int i=0;i<components.length;i++){
            if (components[i].getName().equals(name)) return components[i];
        }
        return null;
    }
}
