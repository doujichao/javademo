package 网络编程和IO.nio;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * this program show how to interrupt a socket channel
 */
public class InterruptibleSocketTest {

    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            JFrame frame=new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}


class InterruptibleSocketFrame extends JFrame{

    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea message;
    private TestServer server;
    private Thread connectThread;

    public InterruptibleSocketFrame (){
        JPanel northPanel=new JPanel();
        add(northPanel,BorderLayout.NORTH);

        final int TEXT_ROWS=20;
        final int  TEXT_COLUMNS=60;
        message=new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
        add(new JScrollPane(message));

        interruptibleButton=new JButton("Interruptible");
        blockingButton=new JButton("Blocking");

        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try{
                    connectInterruptibly();
                }catch (IOException e){
                    message.append("\nInterruptibleSocektTest.connectInterruptibly: "+e );
                }
            });
            connectThread.start();
        });


        blockingButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try{
                    connectBlocking();
                }catch (IOException e){
                    message.append("\nInterruptibleSocektTest.connectInterruptibly: "+e );
                }
            });
            connectThread.start();
        });

        cancelButton=new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event -> {
            connectThread.interrupt();
            cancelButton.setEnabled(false);
        });
        server=new TestServer();
        new Thread(server).start();
        pack();
    }

    /**
     * connects to the test server ,using blocking I/O
     */
    public void connectBlocking() throws IOException {
        message.append("Blocking:\n");
        try(Socket socket=new Socket("localhost",8189)){
            in = new Scanner(socket.getInputStream(),"UTF-8");
            while (!Thread.currentThread().isInterrupted()){
                message.append("Reading ");
                if (in.hasNextLine()){
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                message.append("Socket closed \n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    /**
     * Connects to the test server,using interruptible I/O
     */
    public void connectInterruptibly () throws IOException {
        message.append("Interruptible:\n");
        try(SocketChannel channel=SocketChannel.open(new InetSocketAddress("localhost",8189))){
            in = new Scanner(channel,"UTF-8");
            while (!Thread.currentThread().isInterrupted()){
                message.append("Reading ");
                if (in.hasNextLine()){
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                message.append("Channel closed \n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    /**
     * a mutithreaded server that listens to port 8189 and sends numbers to the client ,simulatin
     * a hanging server after 10 number
     */
    class TestServer implements Runnable{
        @Override
        public void run() {
            try (ServerSocket s=new ServerSocket(8189)){
                while (true){
                    Socket incoming = s.accept();
                    Runnable r=new TestServerHandler(incoming);
                    Thread t=new Thread(r);
                    t.start();
                }
            } catch (IOException e) {
                message.append("\nTestServer.run: "+e);
            }
        }
    }

    class TestServerHandler implements Runnable{

        private Socket incoming;
        private int count;

        public TestServerHandler(Socket incoming) {
            this.incoming=incoming;
        }

        @Override
        public void run() {
            try {
                try {
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out=new PrintWriter(
                            new OutputStreamWriter(outStream,"UTF-8"),true);
                    while (count < 100){
                        count++;
                        if (count <= 10) out.println(count);
                        Thread.sleep(100);
                    }
                } finally {
                    incoming.close();
                    message.append("Closing server\n");
                }

            } catch (Exception e) {
                message.append("\nTestServerHandler.run: "+e);
            }

        }
    }
}
