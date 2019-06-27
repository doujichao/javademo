package spring.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainSingerDemo implements SingerDao{

    private static Logger logger= LoggerFactory.getLogger(PlainSingerDemo.class);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=true&serverTimezone=UTC&characterEncoding=utf-8",
                "root","15937116841");
    }

    private void closeConnection(Connection connection){
        if (connection == null){
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Problem closing connection to the database!",e);
        }
    }

    @Override
    public List<Singer> findAll() {
        List<Singer> result=new ArrayList<>();
        Connection connection=null;
        try {
            connection=getConnection();
            PreparedStatement statement =
                    connection.prepareStatement("select * from singer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Singer singer=new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }
            statement.close();
        } catch (SQLException e) {
           logger.error("Problem when executing Select!",e);
        }finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Singer singer) {
        Connection connection=null;
        try {
            connection=getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into singer(first_name, last_name, birth_date) values " +
                    "(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,singer.getFirstName());
            statement.setString(2,singer.getLastName());
            statement.setDate(3,new java.sql.Date(singer.getBirthDate().getTime()));
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()){
                singer.setId(generatedKeys.getLong(1));
            }
            statement.close();
        } catch (SQLException e) {
            logger.error("Problem when executing Insert!",e);
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long id) {
        Connection connection=null;
        try {
            connection=getConnection();
            PreparedStatement stat = connection.prepareStatement("delete from singer where id = ?");
            stat.setLong(1,id);
            stat.execute();
            stat.close();
        } catch (SQLException e) {
            logger.error("Problem when executing Delete!",e);
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singre) {

    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return null;
    }
}
