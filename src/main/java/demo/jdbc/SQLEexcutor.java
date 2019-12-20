package demo.jdbc;

import java.io.*;
import java.sql.*;

public class SQLEexcutor {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        String sqlFile;
        if (args.length==0){
           sqlFile="D:\\2\\1.sql";
        }else {
            sqlFile=args[0];
        }

        Connection conn = JDBCDemo.getConnection();
        Statement stmt = conn.createStatement();
        BufferedReader br=new BufferedReader(new FileReader (new File(sqlFile)));

        try {
            String data=null;
            StringBuffer sql=new StringBuffer();
            while ((data=br.readLine())!=null){
                //删除开头和结尾的空格
                data=data.trim();
                //如果当前为空行，直接玄幻下一行
                if (data.length()==0)continue;
                sql.append(data);
                if (data.indexOf(";")!=-1){
                    System.out.println(sql.toString());
                    boolean hasResult = stmt.execute(sql.toString());
                    if (hasResult){
                        showResultSet(stmt.getResultSet());
                    }
                   sql.delete(0,sql.length());
                    System.out.println(sql.toString());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.close();
        }
    }

    private static void showResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i=1;i<= columnCount;i++){
            if (i>1) System.out.print(",");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();
        while (resultSet.next()){
            for (int i=1;i<= columnCount;i++){
                if (i>1) System.out.print(",");
                System.out.print(resultSet.getString(i));
            }
            System.out.println();
        }
        resultSet.close();
    }
}
