package spring.mvc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StoreView extends Remote {

    /**
     * 在视图中注册处理各种用户的动作，参数ctrl指定控制器
     * @param ctrl
     */
    void addUserGestureListener(StoreController ctrl) throws  StoreException, RemoteException;

    /**
     * 图形界面显示数据，参数display指定待显示的数据
     * @param display
     */
    void showDisplay(Object display)throws  StoreException, RemoteException;

    /**
     * 当模型层修改了数据库中某个客户的信息的时候，同步刷新视图界面
     */
    void handleCustomerChange(Customer customer)throws  StoreException, RemoteException;


}
