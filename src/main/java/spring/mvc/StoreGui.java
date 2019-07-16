package spring.mvc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

public class StoreGui {

    protected JFrame frame;
    protected Container contentPane;
    protected CardLayout card=new CardLayout();
    protected JPanel carPan=new JPanel();

    //包含各种按钮
    protected JPanel selPan=new JPanel();
    protected JButton custBt=new JButton("客户详细信息");
    protected JButton allCustBt=new JButton("所有客户清单");

    //显示单个客户的面板的组件
    protected JPanel custPan=new JPanel();
    protected JLabel nameLb=new JLabel("客户名称");
    protected JLabel idLb=new JLabel("ID");
    protected JLabel addrLb=new JLabel("地址");
    protected JLabel ageLb=new JLabel("年龄");

    protected JTextField nameTf=new JTextField(25);
    protected JTextField idTf=new JTextField(25);
    protected JTextField addrTf=new JTextField(25);
    protected JTextField ageTf=new JTextField(25);

    protected JButton getBt=new JButton("查询客户");
    protected JButton updBt=new JButton("更新客户");
    protected JButton addBt=new JButton("添加客户");
    protected JButton delBt=new JButton("删除客户");

    //列举所有客户的面板上的组件
    protected JPanel allCustPan=new JPanel();
    protected JLabel allCustLb=new JLabel("所有客户清单",SwingConstants.CENTER);
    protected JTextArea allCustTa=new JTextArea();
    protected JScrollPane allCustSp=new JScrollPane(allCustTa);

    String[] tableHeaders={"ID","姓名","地址","年龄"};
    JTable table;
    JScrollPane tablePane;
    DefaultTableModel tableModel;

    //日志面板上的组件
    protected JPanel logPan=new JPanel();
    protected JLabel logLb=new JLabel("操作日志",SwingConstants.CENTER);

    protected JTextArea logTa=new JTextArea(9,50);
    protected JScrollPane logSp=new JScrollPane(logTa);

    /**
     * 显示单个客户面板
     * @param customer
     */
    public void refreshCustPane(Customer customer){
        showCard("customer");
        if (customer == null || customer.getId() == -1){
            idTf.setText(null);
            nameTf.setText(null);
            addrTf.setText(null);
            ageTf.setText(null);
            return;
        }
        idTf.setText(new Long(customer.getId()).toString());
        nameTf.setText(customer.getName());
        addrTf.setText(customer.getAddr());
        ageTf.setText(new Integer(customer.getAge()).toString());
    }
    /**
     * 显示所有客户面板
     * @param customers
     */
    public void refreshAllCustPane(Set<Customer> customers){
        showCard("customers");
        String newData[][];
        newData=new String[customers.size()][4];
        Iterator<Customer> iterator = customers.iterator();
        int i=0;
        while (iterator.hasNext()){
            Customer cust = iterator.next();
            newData[i][0]=new Long(cust.getId()).toString();
            newData[i][1]=cust.getName();
            newData[i][2]=cust.getAddr();
            newData[i][3]=new Integer(cust.getAge()).toString();
            i++;
        }
        tableModel.setDataVector(newData,tableHeaders);
    }

    /**
     * 在日志面板中logPan中添加日志信息
     * @param msg
     */
    public void updateLog(String msg){
        logTa.append(msg+"\r\n");
    }

    /**
     * 获得客户面板custPan上用户输入的id
     * @return
     */
    public long getCustIdOnCustPan(){
        try {
            return Long.parseLong(idTf.getText().trim());
        } catch (Exception e) {
            updateLog(e.getMessage());
            return -1;
        }
    }

    /**
     * 获得单个客户面板custPan上用户输入的客户信息
     * @return
     */
    public Customer getCustomerOnCustomerPan(){
        try {
            return new Customer(Long.parseLong(idTf.getText().trim()),nameTf.getText().trim(),
                    addrTf.getText().trim(),Integer.parseInt(ageTf.getText().trim()));
        } catch (Exception e) {
            updateLog(e.getMessage());
            return null;
        }
    }

    /**
     * 显示单个面板custPan或者所有客户面板AllCustPan
     * @param customer
     */
    private void showCard(String customer) {
        card.show(carPan,customer);
    }


    /**
     * 构造方法
     */
    public StoreGui(){
        buildDisplay();
    }

    /**
     * 构建图形界面
     */
    private void buildDisplay() {
        frame=new JFrame("商店的客户管理系统");
        buildSelectionPanel();
        buildCustPanel();
        buildAllCustPanel();
        buildLogPanel();

        /*
         * carPan采用CardLayout布局管理器，包括custPan和CustPan两张卡片
         */
        carPan.setLayout(card);
        carPan.add(custPan,"customer");
        carPan.add(allCustPan,"allCustomers");

        //向主窗体中加入各种面板
        contentPane=frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(carPan,BorderLayout.CENTER);
        contentPane.add(selPan,BorderLayout.NORTH);
        contentPane.add(logPan,BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * 创建日志面板
     */
    private void buildLogPanel() {

    }

    /**
     * 创建所有客户allCustPan面板
     */
    private void buildAllCustPanel() {
        allCustPan.setLayout(new BorderLayout());
        allCustPan.add(allCustLb,BorderLayout.NORTH);
        allCustTa.setText("all customer display");

        tableModel=new DefaultTableModel(tableHeaders,10);
        table=new JTable(tableModel);
        tablePane=new JScrollPane(table);
        allCustPan.add(tablePane,BorderLayout.CENTER);

        Dimension dim=new Dimension(500,150);
        table.setPreferredScrollableViewportSize(dim);
    }

    private void buildCustPanel() {

    }

    public void addCustPanelListeners(ActionListener a[]){
        int len=a.length;
        if (len!=4)return;
        getBt.addActionListener(a[0]);
        addBt.addActionListener(a[1]);
        delBt.addActionListener(a[2]);
        updBt.addActionListener(a[3]);
    }

    /**
     * 创建选择面板
     */
    private void buildSelectionPanel() {

    }

    public void addSelectionPanelLiseners(ActionListener a[]){
        int len=a.length;
        if (len!=2) return;
        custBt.addActionListener(a[0]);
        allCustBt.addActionListener(a[1]);
    }
}
