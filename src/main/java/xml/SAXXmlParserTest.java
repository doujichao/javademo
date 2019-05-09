package xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Sax解析xml文件的测试类
 */
public class SAXXmlParserTest {

    public static final SAXParserFactory factory=SAXParserFactory.newInstance();
    public static SAXParser parser;

    static {
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
