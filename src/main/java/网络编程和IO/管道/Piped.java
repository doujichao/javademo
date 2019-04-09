package 网络编程和IO.管道;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入输出流测试类
 */
public class Piped {

    public static void main(String[] args) throws IOException {
        PipedWriter out=new PipedWriter();
        PipedReader in=new PipedReader();

        //将输出流和输入流进行连接，否则在使用是会抛出IOException
        out.connect(in);
        Thread printThread=new Thread(new Print(in),"PrintThread");
        printThread.start();
        int receive=0;
        try {
            while ((receive=System.in.read())!=-1){
                out.write(receive);
            }
        }finally {
            out.close();
        }

    }

    static class Print implements Runnable{

        private PipedReader in;
        public Print(PipedReader id){
            in=id;
        }

        @Override
        public void run() {
            int received=0;

            try {
                while ((received=in.read())!=-1){
                    System.out.print((char) received);
                }
            } catch (IOException e) {

            }
        }
    }
}
