package 算法.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import org.junit.Test;

public class DigestDemo {

	/**
	 * 测试MessageDigest
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void test() throws NoSuchAlgorithmException {
		//待做消息摘要算法的原始信息
		byte[] input ="sha".getBytes();
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(input);
		byte[] digest = sha.digest();
		System.out.println(digest.length+":"+Arrays.toString(digest));
	}
	@Test
	public void test1() throws NoSuchAlgorithmException, IOException {
		byte[] input = "md5".getBytes();
		//初始化MessageDigest对象，将使用MD5算法
		MessageDigest md = MessageDigest.getInstance("MD5");
		//构建DigestInputStream对象
		DigestInputStream dis=new DigestInputStream(new ByteArrayInputStream(input)	,  md);
		dis.read(input,0,input.length);
		byte[] digest = dis.getMessageDigest().digest();
		System.out.println(digest.length+":"+Arrays.toString(digest));
		dis.close();
	}
	@Test
	public void test2() throws NoSuchAlgorithmException, IOException {
		byte[] input = "md5".getBytes();
		//初始化MessageDigest对象，将使用MD5算法
		MessageDigest md = MessageDigest.getInstance("MD5");
		//构建DigestInputStream对象
		DigestOutputStream dis=new DigestOutputStream(new ByteArrayOutputStream()	,  md);
		dis.write(input,0,input.length);
		byte[] digest = dis.getMessageDigest().digest();
		System.out.println(digest.length+":"+Arrays.toString(digest));
		dis.flush();
		dis.close();
	}
	@Test
	public void test3() throws NoSuchAlgorithmException, IOException {
		AlgorithmParameters ap = AlgorithmParameters.getInstance("DES");
		ap.init(new BigInteger("19050619766489163472469").toByteArray());
		byte[] bs = ap.getEncoded();
		System.out.println(new BigInteger(bs).toString());
	}
	@Test
	public void test4() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		//初始化KeyPairGenerator对象
		keyPairGenerator.initialize(1024);
		//生成keyPair对象
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		//获得私钥密钥字节数组，实际使用过程中该密钥以此种形式保存传递給另一方
		byte[] keys = keyPair.getPrivate().getEncoded();
		//由私钥密钥字节数组获得密钥规范
		PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(keys);
		//实例化密钥工厂，并指定RSA算法
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey key = factory.generatePrivate(pkcs8KeySpec);
		System.out.println(key);
	}
	@Test
	public void test5() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		//待做数字签名的原始信息
		byte[] data = "Data Signature".getBytes();
		//实例化keypairGenerator对象，并制定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
		//初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		//生成KeyPair对象
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//实例化Signature对象
		Signature signature = Signature.getInstance(keyPairGen.getAlgorithm());
		//初始化用于签名操作的Signature对象
		signature.initSign(keyPair.getPrivate());
		//更新
		signature.update(data);
		byte[] sign = signature.sign();
		System.out.println(Arrays.toString(sign));
	}
	@Test
	public void test6() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		//待做数字签名的原始信息
		@SuppressWarnings("unused")
		byte[] data = "Data Signature".getBytes();
		//实例化KeyPairGenerator对象，并制定DSA算法
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
		//初始化KeyPairGenerator对象
		keyPairGen.initialize(1024);
		@SuppressWarnings("unused")
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//实例化Signature对象
		Signature signature = Signature.getInstance(keyPairGen.getAlgorithm());
		System.out.println(signature);
	}
}
