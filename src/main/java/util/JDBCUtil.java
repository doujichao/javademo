package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    private static String className="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/mysql?characterEncoding=UTF-8&serverTimezone=UTC";
    private static String username="root";
    private static String password="15937116841";

    private static Connection connection;

    static {
        try {
            //注册驱动
            Class.forName(className);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }


}
