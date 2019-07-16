package util.stream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

/**
 * 内存映射流
 */
public class MemoryMapTest {
    //根据文件名称生成校验和
    public static long chechsumInputStream(Path filename) throws IOException {
        try (InputStream inputStream= Files.newInputStream(filename)){
            CRC32 crc32=new CRC32();
            int c;
            while ((c=inputStream.read())!=-1){
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }


    //根据文件名称生成校验和
    public static long chechsumBufferedInputStream(Path filename) throws IOException {
        try (InputStream inputStream= new BufferedInputStream(Files.newInputStream(filename))){
            CRC32 crc32=new CRC32();
            int c;
            while ((c=inputStream.read())!=-1){
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    //根据文件名称生成随机访问文件校验和
    public static long chechsumRandomAccessFile(Path filename) throws IOException {
        try (RandomAccessFile file=new RandomAccessFile(filename.toFile(),"r")){
            long length = file.length();
            CRC32 crc32=new CRC32();
            for (long p=0;p<length;p++){
                file.seek(p);
                int c=file.readByte();
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    //根据文件名称生成内存映射校验和
    public static long chechsumMapFile(Path filename) throws IOException {
        try (FileChannel channel=FileChannel.open(filename)){
            CRC32 crc32=new CRC32();
            long length = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            for (int p = 0; p< length; p++){
                int c=buffer.get(p);
                crc32.update(c);
            }
            return crc32.getValue();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Input Stream:");
        Path filename = Paths.get(args[0]);
        long start = System.currentTimeMillis();
        long crcValue = chechsumInputStream(filename);
        printResult(start, crcValue);

        System.out.println("Buffered Stream:");
        start=System.currentTimeMillis();
        crcValue = chechsumBufferedInputStream(filename);
        printResult(start, crcValue);

        System.out.println("Random Access Stream:");
        start=System.currentTimeMillis();
        crcValue = chechsumRandomAccessFile(filename);
        printResult(start, crcValue);


        System.out.println("Mapped File Stream:");
        start=System.currentTimeMillis();
        crcValue = chechsumMapFile(filename);
        printResult(start, crcValue);
    }

    private static void printResult(long start, long crcValue) {
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(crcValue));
        System.out.println((end-start)+" milliseconds");
    }
}
