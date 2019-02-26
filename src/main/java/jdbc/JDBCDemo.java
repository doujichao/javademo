package jdbc;


import org.junit.Test;
import util.JDBCUtil;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;

public class JDBCDemo {


    private static String className="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/storeDB?characterEncoding=UTF-8&serverTimezone=UTC";
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

    public static void main(String[] args){
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
        statement.execute("insert into customers (name,age,address) values ('小王',20,'上海')", com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
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
}
