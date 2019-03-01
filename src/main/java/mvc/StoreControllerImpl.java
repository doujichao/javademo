package mvc;

import java.rmi.RemoteException;
import java.util.Set;

public class StoreControllerImpl implements StoreController {

    private StoreModel storeModel;
    private StoreView storeView;

    public StoreControllerImpl(StoreModel model,StoreView view){
        try {
            storeModel=model;
            storeView=view;
            //向视图注册控制器自身
            view.addUserGestureListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reportException(Object o){
        try {
            storeView.showDisplay(o);
        } catch (Exception e) {
            System.out.println("StoreControllerImpl reportException"+e);
        }
    }

    @Override
    public void handleGetCustomerGesture(long id) {
        Customer customer=null;
        try {
            customer=storeModel.getCustomer(id);
            storeView.showDisplay(customer);
        } catch (Exception e) {
           reportException(e);
           customer=new Customer(id);
           try {
               storeView.showDisplay(customer);
           }catch (Exception e1){
               reportException(e1);
           }
        }

    }

    @Override
    public void handleAddCustomerGesture(Customer c) {
        try {
            storeModel.addCustomer(c);
        } catch (Exception e) {
           reportException(e);
        }
    }

    @Override
    public void handleDeleteCustomerGesture(Customer c) {
        try {
            storeModel.deleteCustomer(c);
        } catch (Exception e) {
            reportException(e);
        }
    }

    @Override
    public void handleUpdateCustomerGesture(Customer c) {
        try {
            storeModel.updateCustomer(c);
        } catch (Exception e) {
            reportException(e);
        }
    }

    @Override
    public void handleGetAllCustomersGesture() {
        try {
            Set<Customer> allCustomers = storeModel.getAllCustomers();
            storeView.showDisplay(allCustomers);
        } catch (Exception e) {
            reportException(e);
        }
    }
}
