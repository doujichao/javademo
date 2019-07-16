package spring.security.digest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAdemo {

    @Test
    public void testSHA() throws NoSuchAlgorithmException {
        //代要做消息摘要算法的原始信息
        byte[] input = "hoihoisdhaiohgoa".getBytes();
        //初始化MessageDigest对象,java目前支持md5，和SHA类的摘要算法
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(input);
        //获取摘要结果
        byte[] digest = sha.digest();
        System.out.println(new String(digest).length());
    }

    @Test
    public void testMD5() throws NoSuchAlgorithmException, IOException {
        byte[] bytes = "md5".getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(bytes), md5);
        //流输入
        dis.read(bytes, 0, bytes.length);
        byte[] digest = dis.getMessageDigest().digest();
        System.out.println(new String(digest));
        dis.close();
    }

    @Test
    public void testAlgo() throws NoSuchAlgorithmException, IOException {
        //实例化AlgorithmParameters，并指定DES算法
        AlgorithmParameters des = AlgorithmParameters.getInstance("AES");
        //使用bigInteger生成参数字节数组
        des.init(new BigInteger("12345678").toByteArray());
        //获得参数字节数组
        byte[] b = des.getEncoded();
        System.out.println(new BigInteger(b).toString());
    }
}
