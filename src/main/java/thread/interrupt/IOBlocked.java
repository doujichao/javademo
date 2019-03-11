package thread.interrupt;

import java.io.IOException;
import java.io.InputStream;

public class IOBlocked implements Runnable{
    private InputStream in;
    public IOBlocked(InputStream is){
        in=is;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read():");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            }else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}
