package mvc;

import java.sql.Statement;

public interface DBService {

    /**
     * 获得Statement对象
     * @return
     * @throws Exception
     */
    Statement getStatement() throws Exception;

    /**
     * 关闭Statement对象，以及与之关联的Connection对象
     * @param statement
     */
    void closeStatement(Statement statement);

    /**
     * 执行sql的update，delete，insert语句
     * @throws Exception
     */
    void modifyTable() throws Exception;
}
