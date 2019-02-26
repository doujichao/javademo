package reflect.proxy;

import reflect.remoteReflect.Call;
import reflect.remoteReflect.HelloServer;

import java.util.Date;

/**
 * 静态代理类
 */
public class HelloServiceProxy implements HelloServer {

    private String host;
    private int port;

    public HelloServiceProxy(String host,int port){
        this.host=host;
        this.port=port;
    }

    @Override
    public String echo(String msg) {
        Connector connector=null;
        try {
            connector=new Connector(host,port);
            Call call=new Call("reflect.remoteReflect.HelloService","echo",
                    new Class[]{String.class},new Object[]{msg});
            connector.send(call);
            call= (Call) connector.receive();
            Object result = call.getResult();
            if (result instanceof Throwable){

            }else {
                return (String) result;
            }

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Date getTime() {
        return null;
    }
}
