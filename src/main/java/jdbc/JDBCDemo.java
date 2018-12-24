package jdbc;


import util.JDBCUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class JDBCDemo {
    public static void main(String[] args){
        try {
            Connection conn = JDBCUtil.getConnection();
            //创建call对象
            CallableStatement call =
                    conn.prepareCall("{call sp_count(?)}");
            //注册输出参数
            call.registerOutParameter(1, Types.INTEGER);
            //执行存储过程
            call.execute();
            //取得输出参数
            int i = call.getInt(1);
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
