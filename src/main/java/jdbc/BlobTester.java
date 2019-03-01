package jdbc;

import java.io.*;
import java.sql.*;

public class BlobTester {

    Connection connection;
    public BlobTester(Connection connection){
        this.connection=connection;
    }
    public static void main(String[] args) throws SQLException, IOException {
        Connection conn = JDBCDemo.getConnection();
        BlobTester tester = new BlobTester(conn);
        tester.createTableWithBlob();
        tester.saveBlobToDataBase();
        tester.getBlobFromDatabase();
        conn.close();
    }

    private void createTableWithBlob() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists ABLOB");
        statement.execute("create table ABLOB (id bigint auto_increment primary key," +
                "File mediumblob)");
        statement.close();
    }

    /**
     * 向数据库中保存数据
     */
    private void saveBlobToDataBase() throws SQLException, IOException {
        PreparedStatement ps = connection.prepareStatement("insert into ABLOB(ID,FILE) values (?,?)");
        ps.setLong(1,1);
        FileInputStream fis=new FileInputStream("d:/1/1.jpg");
        ps.setBlob(2,fis,fis.available());
        ps.executeUpdate();
        fis.close();
        ps.close();
    }

    private void getBlobFromDatabase() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select File from ablob where id =1");
        rs.next();
        Blob blob = rs.getBlob(1);

        //把数据库中的blob数据复制到
        InputStream in = blob.getBinaryStream();
        FileOutputStream fos=new FileOutputStream("d:/1/2.jpg");
        byte[] buffer=new byte[1024];
        int len;
        while ((len=in.read(buffer))!=-1){
            fos.write(buffer,0,len);
        }
        fos.close();
        in.close();
        rs.close();
        statement.close();
    }

}
