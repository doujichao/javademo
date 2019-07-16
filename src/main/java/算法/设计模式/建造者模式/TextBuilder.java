package 算法.设计模式.建造者模式;

public class TextBuilder extends Builder {

    /**
     * 文档内容保存在该字段中
     */
    private StringBuffer buffer=new StringBuffer();

    @Override
    public void makeTitle(String title) {
        buffer.append("===============================\n");
        buffer.append("【"+title+"]\n");
        buffer.append("\n");
    }

    @Override
    public void makeString(String str) {
        buffer.append('$'+str+"\n");
        buffer.append("\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (int i=0;i<items.length;i++){
            buffer.append("   ."+items[i]+"\n");
        }
        buffer.append("\n");
    }

    @Override
    public void close() {
        buffer.append("==================================\n");
    }

    public String getResult(){
        return buffer.toString();
    }
}
