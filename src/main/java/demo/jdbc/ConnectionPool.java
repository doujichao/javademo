package demo.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    /**
     * 从连接池中取出连接
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * 把连接放回连接池
     * @param con
     * @throws SQLException
     */
    void releaseConnection(Connection con) throws SQLException;


    /**
     * 关闭连接池
     */
    void close();
}
