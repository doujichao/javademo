package thread.connection;


import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {

    private LinkedList<Connection> pool=new LinkedList<>();

    public ConnectionPool(int initialSize){
        if (initialSize >0){
            for (int i=0;i<initialSize;i++){
                //这里的每个Connection在进行提交的时候会延时一段时间
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (connection != null){
            synchronized (pool){
                //释放连接后需要进行通知，这样消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notify();
            }
        }

    }
    //在mills内无法获取到连接将会返回null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            //完全超时
            if (mills < 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long future=System.currentTimeMillis() + mills;
                long remaining=mills;
                while (pool.isEmpty() && remaining>0){
                    pool.wait(remaining);
                    remaining =future-System.currentTimeMillis();
                }
                Connection result=null;
                if (!pool.isEmpty()){
                    result=pool.removeFirst();
                }
                return result;
            }
        }
    }

}
