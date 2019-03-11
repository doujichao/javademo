package test;


public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Demo d=new Demo();
        new Thread(() -> {
            while (true)
            synchronized (d){
                System.out.println("new Thread");
                try {
                    d.notify();
                    d.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true)
        synchronized (d){
            d.notify();
            System.out.println("main");
            d.wait();
        }
    }
}
