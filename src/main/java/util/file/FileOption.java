package util.file;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOption {

    @Test
    public void testReDelete(){
        File file=new File("G:\\java\\黑马java49期最新(基础班+就业班)");
        List<File> list=new ArrayList<>();
        list.add(file);
        while (list.size()>0){
            //从list中取出一个
            file=list.get(list.size()-1);

            if (file.exists()){
                //文件直接删除
                if (file.isFile()){
                    file.delete();
                    list.remove(file);
                }

                //文件夹装入list中
                if (file.isDirectory()){
                    list.addAll(Arrays.asList(file.listFiles()));
                }

                //是文件夹但是没有内容直接删除
                if (file.isDirectory() && file.listFiles().length==0){
                    file.delete();
                    list.remove(file);
                }
            }else {
                list.remove(file);
            }
        }
    }

}
