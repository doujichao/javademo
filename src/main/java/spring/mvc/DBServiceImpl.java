package spring.mvc;

import demo.jdbc.ConnectionPool;
import demo.jdbc.ConnectionPoolImpl;

import java.sql.Statement;

public class DBServiceImpl implements DBService {

    private ConnectionPool pool;

    public DBServiceImpl(){
        pool=new ConnectionPoolImpl();
    }

    @Override
    public Statement getStatement() throws Exception {
        return pool.getConnection().createStatement();
    }

    @Override
    public void closeStatement(Statement statement) {

    }

    @Override
    public void modifyTable() throws Exception {

    }
}
