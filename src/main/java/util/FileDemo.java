package util;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileDemo {

    @Test
    public void testDeleteTypeFile(){
        String endStr="jpg";
        File file=new File("D:\\迅雷下载\\Lenfried_Cosplay(C74-C85)");
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File fileN:files){
                if (fileN.isDirectory()){
                    continue;
                }else {
                    if (fileN.getName().endsWith(endStr)) {
                        fileN.delete();
                    }
                }
            }
        }else {
            if (file.getName().endsWith(endStr)){
                file.delete();
            }
        }
    }

    @Test
    public void testDeleteFold(){

        File file=new File("D:\\study\\book\\.git\\objects");
        List<File> fileList=new LinkedList<File>();
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
