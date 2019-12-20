package demo.avro;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class Serialize {
    public static void main(String[] args) throws IOException {
        //创建User
        User user=new User();
        user.setName("tom");
        user.setFavoriteNumber(888);
        user.setFavoriteColor("red");

        //通过build创建对象
        User user2=User.newBuilder().setName("tom2").setFavoriteNumber(999)
                .setFavoriteColor("blue").build();

        //通过Write写入文件到磁盘
        DatumWriter<User> userDatumWriter=new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter=new DataFileWriter<>(userDatumWriter);
        dataFileWriter.create(user.getSchema(),new File("user.avro"));
        dataFileWriter.append(user);
        dataFileWriter.append(user2);
        dataFileWriter.close();
        System.out.println("over");
    }
}
