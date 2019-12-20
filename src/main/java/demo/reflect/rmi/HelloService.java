package demo.reflect.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface HelloService extends Remote {
    String echo(String msg)throws RemoteException;
    Date getTime()throws RemoteException;
}
