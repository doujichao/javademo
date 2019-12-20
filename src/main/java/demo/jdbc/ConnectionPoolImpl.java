package demo.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionPoolImpl implements ConnectionPool{

    /**
     * 连接池，可以存放连接
     */
    private final ArrayList<Connection> pool=new ArrayList<>();

    /**
     * 连接池中最多可以容纳的连接数目
     */
    private int poolSize=5;

    public ConnectionPoolImpl(){}

    public ConnectionPoolImpl(int poolSize){
        this.poolSize=poolSize;
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (pool){
            if (!pool.isEmpty()){
                //取出最后一个连接，并返回
                int last=pool.size()-1;
                Connection con = pool.remove(last);
                return con;
            }
        }
        //创建连接并返回
        return JDBCDemo.getConnection();
    }

    @Override
    public void releaseConnection(Connection con) throws SQLException {
        synchronized (pool){
            int currentSize = pool.size();
            if (currentSize < poolSize){
                pool.add(con);
                return;
            }
        }
        con.close();
    }

    protected void finalize(){
        close();
    }

    @Override
    public void close() {
        Iterator<Connection> iterator = pool.iterator();
        while (iterator.hasNext()){
            try {
                iterator.next().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        pool.clear();
    }
}
