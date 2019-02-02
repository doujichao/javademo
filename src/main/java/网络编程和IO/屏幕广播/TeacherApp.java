package 网络编程和IO.屏幕广播;


public class TeacherApp {

    public static void main(String[] args){
        TeacherSender sender=new TeacherSender("localhost",9999);
        sender.broadcastScreen();
    }
}
