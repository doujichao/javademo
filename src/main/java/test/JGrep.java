package test;

import java.util.Arrays;

public class JGrep {
    public static void main(String[] args){
        System.out.println(Arrays.toString(change(new byte[3])));
        Pig[] ps=new Pig[2];
        ps[1]=new Pig("");
    }

    private static byte[] change(byte[] buff){
        for (int i=0;i<buff.length;i++){
            int b=0;
            for (int j=0;j<8;j++){
                int bit=(buff[i] >> j & 1) ==0?1:0;
                b+=(1<<j)*bit;
            }
            buff[i]=(byte)b;
        }
        return buff;
    }

    static class  Pig{
        public Pig(String s){}
    }
}
