package java.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionPoolImpl2 implements ConnectionPool {

   private final ArrayList<Connection> pool=new ArrayList<>();
   private int poolSize=5;
   public ConnectionPoolImpl2(){}
   public ConnectionPoolImpl2(int poolSize){
       this.poolSize=poolSize;
   }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (pool){
            if (!pool.isEmpty()){
                int last=pool.size()-1;
                return pool.remove(last);
            }
        }
        Connection connection = JDBCDemo.getConnection();
        return getProxy(connection,this);//返回动态代理连接
    }

    private Connection getProxy(final Connection connection, final ConnectionPool pool) {
        InvocationHandler handler=new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("close")){
                    pool.releaseConnection(connection);
                    return null;
                }else {
                    return method.invoke(connection,args);
                }
            }
        };

       return (Connection) Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(),new Class[]{ConnectionPool.class},handler);
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
