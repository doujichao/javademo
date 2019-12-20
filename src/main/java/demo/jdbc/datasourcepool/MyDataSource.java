package demo.jdbc.datasourcepool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class MyDataSource  implements DataSource {

    public static final int LEN=10;
    static String url;
    static String username;
    static String password;

    private List<MyConnection> pool;
    private Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            url="java.jdbc:mysql://127.0.0.1:3306/mysql?characterEncoding=UTF-8&serverTimezone=UTC";
            username="root";
            password="15937116841";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MyDataSource(){
        try {
            pool=new LinkedList<MyConnection>();
            for(int i=0;i<LEN;i++){
                connection= DriverManager.getConnection(url,username,password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(pool.size()<=0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pool.remove(0);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
