package reflect.proxy;

import java.io.*;
import java.net.Socket;

public class Connector {
    private String host;
    private int port;
    private Socket socket;
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;
    public Connector (String host,int port) throws IOException {
        this.host=host;
        this.port=port;
        connect(host,port);
    }

    /**
     * 发送对象
     * @param obj
     * @throws IOException
     */
    public void send(Object obj) throws IOException {
        oos.writeObject(obj);
    }

    /**
     * 接收对象
     * @return
     */
    public Object receive() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    /**
     * 建立远程连接
     */
    public void connect() throws IOException {
        connect(host,port);
    }

    private void connect(String host, int port) throws IOException {
        socket=new Socket(host, port);
        os=socket.getOutputStream();
        oos=new ObjectOutputStream(os);
        is=socket.getInputStream();
        ois=new ObjectInputStream(is);
    }

    public void close(){
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Connection.close"+e);
        }
    }
}
