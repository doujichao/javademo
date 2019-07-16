package 算法.设计模式.窗口模式;

import java.io.IOException;
import java.io.Writer;

/**
 * 用于编写简单的web页面
 */
public class HtmlWriter {

    private Writer writer;

    public HtmlWriter(Writer writer) {
        this.writer = writer;
    }

    public void title(String title) throws IOException {
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<title>"+title+"</title>");
        writer.write("</head><body\n><h1>"+title+"</h1>\n");
    }

    public void paragraph(String msg) throws IOException {
        writer.write("<p>"+msg+"</p>");
    }

    public void line(String href,String caption) throws IOException {
        paragraph("<a href='"+href+"' >"+caption+"</a>");
    }

    public void mailto(String mailaddr,String username) throws IOException {
        line("mailto:"+mailaddr,username);
    }

    public void close() throws IOException {
        writer.write("</body>");
        writer.write("</html>\n");
        writer.close();
    }

}
