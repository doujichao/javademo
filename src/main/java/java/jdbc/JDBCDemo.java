package java.jdbc;


import org.junit.Test;
import util.JDBCUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;

public class JDBCDemo extends JFrame implements ActionListener {

    private Statement stmt;
    private ResultSet resultSet;

    private JLabel rowLabel=new JLabel();
    private JTextField idTxtFid=new JTextField();
    private JTextField nameTxtFid=new JTextField();
    private JTextField ageTxtFid=new JTextField();
    private JTextField addressTxtFid=new JTextField();

    private JLabel idLabel=new JLabel("id");
    private JLabel nameLabel=new JLabel("name");
    private JLabel ageLabel=new JLabel("age");
    private JLabel addressLabel=new JLabel("address");

    private JButton firstBt=new JButton("first");
    private JButton previousBt=new JButton("previous");
    private JButton nextBt=new JButton("next");
    private JButton lastBt=new JButton("last");
    private JButton insertBt=new JButton("insert");
    private JButton deleteBt=new JButton("delete");
    private JButton updateBt=new JButton("update");

    private JPanel headPanel=new JPanel();
    private JPanel centerPanel=new JPanel();
    private JPanel bottomPanel=new JPanel();

    private static String className="com.mysql.java.jdbc.Driver";
    private static String url="java.jdbc:mysql://127.0.0.1:3306/storeDB?characterEncoding=UTF-8&serverTimezone=UTC";
    private static String username="root";
    private static String password="15937116841";

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    static {
        try {
            //注册驱动
            Class.forName(className);
            connection = DriverManager.getConnection(url, username, password);
            DriverManager.setLogWriter(new PrintWriter(new FileOutputStream("d:/2/2.log"),true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JDBCDemo(String title) throws SQLException {
        super(title);
        stmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        resultSet=stmt.executeQuery("select id,name,age,address from customers");
        if (resultSet.next())refresh();
        buildDisplay();

    }

    private void buildDisplay() {
        firstBt.addActionListener(this);
        previousBt.addActionListener(this);
        nextBt.addActionListener(this);
        lastBt.addActionListener(this);
        insertBt.addActionListener(this);
        updateBt.addActionListener(this);
        deleteBt.addActionListener(this);

        Container contentPane=getContentPane();
        headPanel.add(rowLabel);
        centerPanel.setLayout(new GridLayout(4,2,2,2));
        centerPanel.add(idLabel);
        contentPane.add(idTxtFid);

        idTxtFid.setEnabled(false);
        centerPanel.add(nameLabel);
        centerPanel.add(nameTxtFid);
        centerPanel.add(ageLabel);
        centerPanel.add(ageTxtFid);
        centerPanel.add(addressLabel);
        centerPanel.add(addressTxtFid);

        bottomPanel.add(firstBt);
        bottomPanel.add(previousBt);
        bottomPanel.add(nextBt);
        bottomPanel.add(lastBt);
        bottomPanel.add(insertBt);
        bottomPanel.add(updateBt);
        bottomPanel.add(deleteBt);

        contentPane.add(headPanel,BorderLayout.NORTH);
        contentPane.add(centerPanel,BorderLayout.CENTER);
        contentPane.add(bottomPanel,BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //关闭数据库连接
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } System.exit(0);
            }
        });
        pack();
        setVisible(true);
    }

    /**
     * 刷新页面的数据
     */
    private void refresh() throws SQLException {
        int row = resultSet.getRow();
        rowLabel.setText("显示第"+row+"条记录");
        if (row==0){
            idTxtFid.setText("");
            nameTxtFid.setText("");
            ageTxtFid.setText("");
            addressTxtFid.setText("");
        }else {
            idTxtFid.setText(new Long(resultSet.getLong(1)).toString());
            nameTxtFid.setText(resultSet.getString(2));
            ageTxtFid.setText(new Integer(resultSet.getInt(3)).toString());
            addressTxtFid.setText(resultSet.getString(4));
        }
    }



    @Test
    public void testDataBaseMate() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println("允许的最大连接数："+metaData.getMaxConnections());
    }

    public static void main(String[] args) throws SQLException {
        new JDBCDemo("演示Result的用法");
    }
    @Test
    public void testCall(){
        try {
            Connection conn = JDBCUtil.getConnection();
            //创建call对象
            CallableStatement call =
                    conn.prepareCall("{call sp_count(?)}");
            //注册输出参数
            call.registerOutParameter(1, Types.INTEGER);
            //执行存储过程
            call.execute();
            //取得输出参数
            int i = call.getInt(1);
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into customers (id,name,age,address) values (?,?,?,?)");
        ps.setInt(1,4);
        ps.setString(2,"张三");
        ps.setInt(3,33);
        ps.setString(4,"成都");
        ps.execute();
    }

    @Test
    public void testSelect() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from customers");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String address = rs.getString("address");
            System.out.println(id+":"+name+":  "+age+": "+address);
        }
    }

    @Test
    public void testGetKey() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("insert into customers (name,age,address) values ('小王',20,'上海')", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()){
            System.out.println("id="+rs.getInt(1));
        }

        rs.close();
        statement.close();
        connection.close();
    }

    @Test
    public void testFetchProp() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from customers");
        //设置批量抓取的大小
        rs.setFetchSize(100);
        /*
         *设置抓取的方向；
         * ResultSet.FETCH_FORWARD（单项）
         * ResultSet.FETCH_REVERSE（双向）
         * ResultSet.FETCH_UNKNOWN（未知）
         */
        rs.setFetchDirection(ResultSet.FETCH_FORWARD);
        System.out.println(connection.getMetaData());
    }

    /**
     * ResultSet.TYPE_FORWARD_ONLY:结果集只能从上往下移动，不能滚动，这是默认值
     * ResultSet.TYPE_SCROLL_INSENSITIVE:结果集可以上下移动，对于修改不敏感
     * ResultSet.TYPE_SCROLL_SENSITIVE:结果集可以上下移动，对于修改敏感，比如删除一条数据的时候
     *                                  游标位置会随之变化
     *
     *CONCUR_READ_ONLY:结果集不能被更新
     *CONCUR_UPDATABLE:结果集可以被更新
     *
     */
    @Test
    public void testResult() throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = statement.executeQuery("select * from Customers");
        System.out.println(rs.getType());
        System.out.println(rs.getConcurrency());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b= (JButton) e.getSource();
        try{
            if (b.getText().equals("first")){
                resultSet.first();
            }else if (b.getText().equals("last")){
                resultSet.last();
            }else if (b.getText().equals("next")){
                if (resultSet.isLast())return;
                resultSet.next();
            }else if (b.getText().equals("previous")){
                if (resultSet.isFirst())return;
                resultSet.previous();
            }else if (b.getText().equals("update")){
                changeData();
                resultSet.updateRow();
            }else if (b.getText().equals("delete")){
                resultSet.deleteRow();
                resultSet.first();
            }else if (b.getText().equals("insert")){
                //把游标移动到待插入的位置
                resultSet.moveToInsertRow();
                changeData();
                resultSet.insertRow();
                //把游标移动到插入前的位置
                resultSet.moveToCurrentRow();
            }
            refresh();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    private void changeData() throws SQLException {
        resultSet.updateString("name",nameTxtFid.getText());
        resultSet.updateInt("age",Integer.parseInt(ageTxtFid.getText()));
        resultSet.updateString("address",addressTxtFid.getText());
    }
}
