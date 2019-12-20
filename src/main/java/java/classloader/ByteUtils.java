package java.classloader;

public class ByteUtils {
    public static int byte2Int(byte[] b, int start, int len) {
        int sum=0;
        int end=start+len;
        for (int i=start;i<end;i++){
            //只取后四位有效数字
            int n=((int)b[i]) & 0xff;
            //
            n <<= (--len) * 8;
            sum=n+sum;
        }
        return sum;
    }

    public static byte[] int2Byte(int value,int len){
        byte[] b=new byte[len];
        for (int i=0;i<len;i++){
            b[len-i-1]= (byte) ((value >> 8*i) & 0xff);
        }
        return b;
    }

    public static String byte2String(byte[] b,int start,int len){
        return new String(b,start,len);
    }
    public static byte[] string2Bytes(String str){
        return str.getBytes();
    }

    /**
     * 将字节进行替换
     * @param originalBytes 源字节数组
     * @param offset 偏移量
     * @param len 代替换字节的长度
     * @param replaceBytes 代替换字节
     * @return 替换之后的字节数组
     */
    public static byte[] bytesReplace(byte[] originalBytes,int offset
            ,int len,byte[] replaceBytes){
        byte[] newBytes=new byte[originalBytes.length+replaceBytes.length-len];
        //将原数组里面的数据存入newBytes
        System.arraycopy(originalBytes,0,newBytes,0,offset);
        //将被替换数组的值存入newBytes
        System.arraycopy(replaceBytes,0,newBytes,offset,replaceBytes.length);
        /**
         * -------------------(originalBytes)
         *              ^*****^ 这里是补足这一部分
         *       -------------------------------(replaceBytes)
         * --------------------------(newBytes)
         *       ^——————^(len)
         *     offset
         */
        System.arraycopy(originalBytes,offset+len,newBytes,offset+replaceBytes.length,
                originalBytes.length-offset-len);
        return newBytes;
    }
}
