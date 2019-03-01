package mvc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface StoreModel extends Remote {
    /**
     * 注册视图，以便当模型修改了数据库中的客户信息的时候，可以回调视图刷新界面的方法
     * @param storeView
     * @throws StoreException
     * @throws RemoteException
     */
    void addChangeListener(StoreView storeView) throws StoreException, RemoteException;

    /**
     * 向数据库中添加一个新的客户
     * @param customer
     * @throws StoreException
     * @throws RemoteException
     */
    void addCustomer(Customer customer) throws  StoreException, RemoteException;


    /**
     * 从数据库中删除一个客户
     * @param customer
     * @throws StoreException
     * @throws RemoteException
     */
    void deleteCustomer(Customer customer) throws  StoreException, RemoteException;

    /**
     * 跟新数据库中的客户
     * @throws StoreException
     * @throws RemoteException
     */
    void updateCustomer(Customer customer)throws  StoreException, RemoteException;

    /**
     * 根据参数id检索客户
     * @param id
     * @return
     * @throws StoreException
     * @throws RemoteException
     */
    Customer getCustomer(long id)throws  StoreException, RemoteException;

    /**
     * 返回数据库中所有的客户清单
     * @return
     * @throws StoreException
     * @throws RemoteException
     */
    Set<Customer> getAllCustomers() throws  StoreException, RemoteException;

}
