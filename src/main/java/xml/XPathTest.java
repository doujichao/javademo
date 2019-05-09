package xml;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/**
 * DOM解析xml和Xpath获取信息
 */
public class XPathTest {

    private static final DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    private static DocumentBuilder build = null;
    private static final XPathFactory xpFactory=XPathFactory.newInstance();
    private static XPath xPath=xpFactory.newXPath();

    static {
        try {
            build = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Document doc = build.parse(new File("fontdialog.xml"));
        String s = xPath.evaluate("count(/gridbag/row)", doc);
        System.out.println(s);
        String s1 = xPath.evaluate("/gridbag/row[1]/cell[1]/bean[1]/class", doc);
        System.out.println(s1);
    }
}
