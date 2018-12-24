package com.test;

import util.Util;

public class Test {

    @org.junit.Test
    public void testLong2ByteArr(){
        byte[] bytes = Util.long2ByteArr(9999999);
        long l = Util.byteArr2Long(bytes,0);
        System.out.println(l);

        byte[] bytes1 = Util.int2Bytes(5);
        int i = Util.byte2Int(bytes1, 0);
        System.out.println(i);

    }
}
