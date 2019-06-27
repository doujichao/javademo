package spring.jdbc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConfigTest {

    private static Logger logger= LoggerFactory.getLogger(DbConfigTest.class);

    @Test
    public void testOne() throws SQLException {
        GenericXmlApplicationContext context=new GenericXmlApplicationContext(
                "classpath:jdbcApplication.xml");
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        testDataSource(dataSource);
        context.close();
    }


    @Test
    public void testTwo() throws SQLException {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
        context.register(DBConfig.class);
        context.refresh();
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        testDataSource(dataSource);
        context.close();

    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        Connection connection=null;
        try {
            connection=dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT  1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int mockVal=resultSet.getInt("1");
                assert mockVal==1;
            }
            statement.close();
            logger.info("测试结束");
        } catch (SQLException e) {
            logger.debug("Something unexpected happended.",e);
        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }

}
