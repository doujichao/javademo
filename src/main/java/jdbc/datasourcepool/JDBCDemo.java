package jdbc.datasourcepool;

import com.sun.rowset.CachedRowSetImpl;
import org.junit.Test;
import util.JDBCUtil;

import javax.sql.rowset.CachedRowSet;
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


    /**
     * 每个数据库连接都有一个全局变量@@autocommit表示当前事务
     * 0：表示手工提交模式
     * 1：表示自动提交模式
     */
    @Test
    public void testCommit() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.supportsBatchUpdates());
        System.out.println(metaData.supportsSavepoints());

        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("delete from accounts");
        statement.addBatch("insert into accounts(id,name,balance)" +
                "values (1,'Tom',1000)");

        statement.addBatch("insert into accounts(id,name,balance)" +
                "values (2,'Jack',1000)");
        statement.executeBatch();
        connection.commit();
        statement.addBatch("update accounts set balance=900 where id=1");
        statement.executeBatch();
        Savepoint savepoint = connection.setSavepoint();
        statement.addBatch("update accounts set balance=900 where id=1");
        statement.executeBatch();
        connection.rollback(savepoint);
        connection.commit();
    }


    @Test
    public void testDataBaseMate() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        connection.createStatement();
        System.out.println("连接数："+metaData.getMaxConnections());
        System.out.println(metaData.getDriverVersion());
        System.out.println(metaData.getDriverName());

    }

    /**
     * 创建存储过程
     * 这里需要注意，需要使用Delimiter改变结尾符，不然里面的";"会报错
     *
     * DELIMITER //
     * CREATE PROCEDURE demoSp(IN inputParam VARCHAR(255),INOUT inOutParam INT)
     * BEGIN
     * 	DECLARE z INT DEFAULT 0;
     * 	SET z=inOutParam + 1;
     * 	SET inOutParam=z;
     * 	SELECT CONCAT('hello',inputParam);
     * END //
     * DELIMITER ;
     */
    @Test
    public void testCall2() throws SQLException {
        //创建一个调用demoSp的存储过程的CallableStatement对象
        CallableStatement call = connection.prepareCall("{call demoSp(?,?)}");
        //设置第一个参数的值
        call.setString(1,"Tom");
        //注册第二个参数的类型
        call.registerOutParameter(2,Types.INTEGER);
        //设置第二个参数的值
        call.setInt(2,1);
        //执行存储过程
        boolean hadResults = call.execute();
        //访问结果集
        if (hadResults){
            ResultSet rs = call.getResultSet();
            showResult(rs);
        }

    }

    private void showResult(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i=1;i<=columnCount;i++){
            if (i > 1) System.out.print(",");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();
        while (rs.next()){
            for (int i=1;i< columnCount;i++){
                if (i>1) System.out.print(",");
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
        rs.close();
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
        statement.execute("insert into customers (name,age,address) values ('小王',20,'上海')",Statement.RETURN_GENERATED_KEYS);
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

    @Test
    public void testCachedRowSet() throws SQLException {
        CachedRowSet rowSet=new CachedRowSetImpl();
    }
}
