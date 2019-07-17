package 算法.设计模式.代理模式;

public class PrintereProxy  implements Printable{
    //名字
    private String name;
    //本人
    private Printer real;

    public PrintereProxy(){}

    public PrintereProxy(String name){
        this.name=name;
    }

    @Override
    public void setPrinterName(String name) {
        if (real!=null){
            //设置本人名字
            real.setPrinterName(name);
        }
        this.name=name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String string) {
        realize();
        real.print(string);
    }

    private void realize() {
        if (real==null){
            real=new Printer(name);
        }
    }
}
