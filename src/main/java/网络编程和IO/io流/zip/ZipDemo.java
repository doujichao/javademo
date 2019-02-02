package 网络编程和IO.io流.zip;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDemo {

    public static void main(String[] args){

        try {
            FileInputStream fis=new FileInputStream("F:\\赠送算法必备书籍（必看）\\2.算法概论.pdf");
            FileOutputStream fos=new FileOutputStream("f:/c.zip");
            ZipOutputStream zos=new ZipOutputStream(fos);

            ZipEntry entry=new ZipEntry("F:\\赠送算法必备书籍（必看）\\2.算法概论.pdf");
            zos.putNextEntry(entry);
            byte[] bys=new byte[1024*1024];
            int len=-1;
            while((len=fis.read(bys))!=-1){
                zos.write(bys,0,len);
            }
            zos.close();
            fos.close();
            fis.close();
            System.out.println("压缩成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unzip(){
        try {
            ZipInputStream zis=new ZipInputStream(new  FileInputStream("f:/c.zip"));
            ZipEntry zipEntry=null;
            byte[] bys=new byte[1024*1024];
            int len=-1;
            while((zipEntry=zis.getNextEntry())!=null){
                FileOutputStream fos=new FileOutputStream("e:/1/"+zipEntry.getName());
                while((len=zis.read(bys))!=-1){
                    fos.write(bys,0,len);
                }
                fos.close();
            }
            zis.close();
            System.out.println("解压完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
