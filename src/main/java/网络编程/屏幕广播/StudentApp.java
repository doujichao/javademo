package 网络编程.屏幕广播;

public class StudentApp {
    public static void main(String[] args){
        StudentUI ui=new StudentUI();
        new StudentReceiverThread(9999,ui).start();
    }
}
