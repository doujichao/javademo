package 算法.设计模式.享元模式;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BigChar {
    //字符名称
    private char charname;
    //大型字符对应的字符串（由'#','.','\n'组成）
    private String fontdata;
    public BigChar(char charname){
        this.charname=charname;
        try {
            BufferedReader reader=new BufferedReader(new FileReader(
               "big"+charname+".txt"
            ));
            String line;
            StringBuffer buffer=new StringBuffer();
            while ((line=reader.readLine())!=null){
                buffer.append(line);
                buffer.append("\n");
            }
            reader.close();
            this.fontdata=buffer.toString();
        } catch (IOException e) {
            this.fontdata=charname+"?";
        }
    }

    public void print(){
        System.out.println(fontdata);
    }
}
