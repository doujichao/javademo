package reflect.remoteReflect;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleServer {

    /**
     * 用于存放远程对象的缓存
     */
    private Map remoteObjects=new HashMap();

    /**
     * 将一个远程对象放到缓存中
     */
    public void register(String className,Object remoteObject){
        remoteObjects.put(className,remoteObject);
    }

    public void service() throws Exception {
        ServerSocket serverSocket=new ServerSocket(8000);
        System.out.println("服务器启动");
        while (true){
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);

            //接收客户端发送的Call对象
            Call call = (Call) ois.readObject();
            System.out.println(call);
            //调用相关对象的方法
            call=invoke(call);
            oos.writeObject(call);
            ois.close();
            oos.close();
            socket.close();
        }

    }

    private Call invoke(Call call) {

        Object result=null;
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class[] paramTypes = call.getParamTypes();
            Class<?> classType = Class.forName(className);
            Method method = classType.getMethod(methodName, paramTypes);
            Object remoteObject = remoteObjects.get(className);
            if (remoteObject==null){
                throw new Exception("远程对象不存在");
            }else {
                result=method.invoke(remoteObject,params);
            }
        }catch (Exception e) {
            e.printStackTrace();
            result=e;
        }
        call.setResult(result);
        return call;
    }

    public static void main(String[] args) throws Exception {
        SimpleServer server=new SimpleServer();
        //把实现创建的HelloServiceImpl对象加入到服务器的缓存中
        server.register("reflect.remoteReflect.HelloServer",new HelloServiceImpl());
        server.service();
    }
}
