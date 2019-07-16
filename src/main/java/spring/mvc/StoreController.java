package spring.mvc;

public interface StoreController {

    /**
     * 处理根据id查询客户的动作
     * @param id
     */
    void handleGetCustomerGesture(long id);

    /**
     * 处理添加客户的动作
     * @param c
     */
    void handleAddCustomerGesture(Customer c);

    /**
     * 处理删除客户的动作
     * @param c
     */
    void handleDeleteCustomerGesture(Customer c);

    /**
     * 处理更新客户的动作
     * @param c
     */
    void handleUpdateCustomerGesture(Customer c);

    /**
     * 处理列出所有的客户清单的动作
     */
    void handleGetAllCustomersGesture();
}
