package 算法.设计模式.窗口模式;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 可获取指定数据源
 */
public class Database {
    private Database(){}
    public static Properties getProperties(String name){
        String filename=name+".txt";
        Properties prop=new Properties();
        try {
            prop.load(new FileInputStream(filename));
        } catch (IOException e) {
            System.out.println("Waring :"+filename+" is not found");
        }
        return prop;
    }
}
