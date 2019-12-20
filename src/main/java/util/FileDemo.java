package util;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileDemo {

    @Test
    public void testDeleteFold(){

        File file=new File("D:\\game\\ra3 - 副本");
        List<File> fileList=new LinkedList<>();
        fileList.add(file);
        while (fileList.size()>0){
            File last = ((LinkedList<File>) fileList).getLast();

            //如果该文件为文件
            if (last.isFile()){
                if (last.delete()){
                    fileList.remove(last);
                }
                continue;
            }

            //如果是文件夹的话
            if (last.isDirectory()){
                File[] files = last.listFiles();
                if (files!=null && files.length>0){
                    fileList.addAll(Arrays.asList(files));
                    continue;
                }else {
                    last.delete();
                    fileList.remove(last);
                }

            }
        }

    }

}
