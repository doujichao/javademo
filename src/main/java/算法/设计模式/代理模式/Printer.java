package 算法.设计模式.代理模式;

public class Printer implements Printable {

    private String name;

    public Printer(){
        heavyJob("正在生成Printer的实例");
    }

    public Printer(String name){
        this.name=name;
        heavyJob("正在生成Printer的实例（"+name+")");
    }
    private void heavyJob(String msg) {
        System.out.print(msg);
        for (int i=0;i<5;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.print(".");
        }
        System.out.println("结束。");
    }

    @Override
    public void setPrinterName(String name) {
        this.name=name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String string) {
        System.out.println("=== "+name+" ===");
        System.out.println(string);
    }
}
