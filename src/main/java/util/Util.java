package util;

public class Util {
    /**
     * 将long装换成字节数组
     * @param l
     * @return
     */
    public static byte[] long2ByteArr(long l){
        byte[] bys=new byte[8];
        for(int i=7;i>=0;i--){
            bys[i] =(byte) (l >> (i*8));
        }
        return bys;
    }

    /**
     * 将整数转成byte数组
     */
    public static byte[] int2Bytes(int i) {
        byte[] arr = new byte[4] ;
        for(int j=3;j>=0;j--){
            arr[j]= (byte)(i>>(j*8));
        }
        return arr ;
    }

    public static long byteArr2Long(byte[] bytes,int offset){
        long result=0;
        for(int i=7;i>=0;i--){
            result |=((long)bytes[i+offset] &  0xFF) << (i*8);
        }
        return result;
    }

    /**
     * 字节数组转成整数
     * @param bytes 待转换的数组
     * @return 转换成的数字
     */
    public static int byte2Int(byte[] bytes,int offset) {
        int result=0;
        for(int i=3;i>=0;i--){
            result |= (bytes[i+offset] & 0xFF) <<( i * 8 );
        }
        return result ;
    }

}
