package java.reflect.remoteReflect;

import java.io.*;
import java.net.Socket;

public class SimpleClient {

    public void invoke() throws IOException, ClassNotFoundException {
        Socket socket=new Socket("localhost",8000);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(out);
        InputStream in = socket.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(in);

        //包装需要调用的方法名和参数列表
        Call call=new Call("java.reflect.remoteReflect.HelloService","echo",
                new Class[]{String.class},new Object[]{"hello"});
        //向服务器发送call对象
        oos.writeObject(call);
        call= (Call) ois.readObject();
        System.out.println(call.getResult());
        ois.close();
        oos.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new SimpleClient().invoke();
    }

}
