package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Set;

public class StoreViewImpl extends UnicastRemoteObject implements StoreView, Serializable {

    private transient StoreGui gui;
    private StoreModel storeModel;
    private Object display;
    private ArrayList<StoreController> storeControllers=new ArrayList<>(10);

    protected StoreViewImpl() throws RemoteException {
    }

    public StoreViewImpl(StoreModel model) throws RemoteException {
        this();
        try {
            storeModel=model;
            model.addChangeListener(this);
        } catch (StoreException e) {
            System.out.println("StoreViewImpl constructor"+e);
        }

        gui=new StoreGui();

    }
    //注册控制器
    @Override
    public void addUserGestureListener(StoreController ctrl) throws StoreException, RemoteException {
        storeControllers.add(ctrl);
    }

    @Override
    public void showDisplay(Object display) throws StoreException, RemoteException {
        if (!(display instanceof  Exception)) this.display=display;
        if (display instanceof Customer) gui.refreshCustPane((Customer)display);
        if (display instanceof Set) gui.refreshAllCustPane((Set<Customer>)display);
        if (display instanceof Exception) gui.updateLog(((Exception)display).getMessage());
    }

    //刷新界面上的客户信息
    @Override
    public void handleCustomerChange(Customer customer) throws StoreException, RemoteException {
        long cIdOnPan=-1;

        try {
            if (display instanceof Set){
                gui.refreshAllCustPane(storeModel.getAllCustomers());
                return;
            }
            if (display instanceof  Customer){
                cIdOnPan=gui.getCustIdOnCustPan();
                if (cIdOnPan != customer.getId()) return;
            }

            gui.refreshCustPane(customer);
        }catch (Exception e){
            System.out.println("StoreViewImpl processCustomer"+e);
        }
    }

    //监听图形界面上【查询客户】按钮的ActionEvent的监听器
    transient ActionListener custGetHandler= e -> {
        StoreController sc;
        long custId;
        custId=gui.getCustIdOnCustPan();

        for (int i=0;i<storeControllers.size();i++){
            sc=storeControllers.get(i);
            sc.handleGetCustomerGesture(custId);
        }

    };
    //监听图形界面上【添加客户】按钮的ActionEvent的监听器
    transient ActionListener custAddandler= e -> {
        StoreController sc;
        Customer customer = gui.getCustomerOnCustomerPan();

        for (int i=0;i<storeControllers.size();i++){
            sc=storeControllers.get(i);
            sc.handleAddCustomerGesture(customer);
        }

    };
    //监听图形界面上【删除客户】按钮的ActionEvent的监听器
    transient ActionListener custDeleteHandler= e -> {
        StoreController sc;
        Customer customer = gui.getCustomerOnCustomerPan();

        for (int i=0;i<storeControllers.size();i++){
            sc=storeControllers.get(i);
            sc.handleDeleteCustomerGesture(customer);
        }

    };
    //监听图形界面上【查询客户】按钮的ActionEvent的监听器
    transient ActionListener custUpdateHandler= e -> {
        StoreController sc;
        Customer customer = gui.getCustomerOnCustomerPan();

        for (int i=0;i<storeControllers.size();i++){
            sc=storeControllers.get(i);
            sc.handleUpdateCustomerGesture(customer);
        }
    };

    /**
     * 监听图形界面上【客户详细信息】按钮的ActionEvent的监听器
     */
    transient ActionListener custDetialsPageHandler = e -> {
      StoreController sc;
      long custId;
      custId=gui.getCustIdOnCustPan();
      if (custId == -1){
          try {
              showDisplay(new Customer(-1));
          } catch (StoreException e1) {
              e1.printStackTrace();
          } catch (RemoteException e1) {
              e1.printStackTrace();
          }
      }else {
          for (int i=0;i<storeControllers.size();i++){
              sc=storeControllers.get(i);
              sc.handleGetCustomerGesture(custId);
          }
      }
    };

    /**
     * 监听图形界面上【所有客户清单】按钮的ActionEvent的监听器
     */
    transient ActionListener allCustsPageHandler=e -> {
        StoreController sc;
        Customer customer = gui.getCustomerOnCustomerPan();

    };

    /**
     * 负责监听单个客户面板custPan上的所有按钮的ActionEvent事件的监听器
     */
    transient ActionListener custPanelListeners[] = {custGetHandler,custAddandler,custDeleteHandler
    ,custUpdateHandler};

    /**
     * 负责监听选择面板selPan上的所有按钮的ActionEvent是阿锦的监听器
     */
    transient ActionListener selectionPanelListeners[]={custDetialsPageHandler,allCustsPageHandler};
}
