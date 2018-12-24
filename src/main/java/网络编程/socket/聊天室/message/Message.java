package 网络编程.socket.聊天室.message;


import util.Util;

public class Message {

    /**
     * 消息类型
     */
    public static final byte TYPE_MESSAGE=0;
    /**
     * 朋友列表类型
     */
    public static final byte TYPE_FRIEND=2;
    /**
     * 文件类型
     */
    public static final byte TYPE_FILE=1;

    /**
     * 文件类型
     */
    private byte mType;

    /**
     * 文件长度
     */
    private int mLen;

    /**
     * 具体文件内容
     */
    private byte[] mContent;

    /**
     * 获取组装好的报文
     * @return 组装好的报文
     */
    public byte[] getByteArrays(){
        byte[] bys=new byte[1+4+mContent.length];
        bys[0]=mType;
        byte[] bytes = Util.int2Bytes(mLen);
        System.arraycopy(bytes,0,bys,1,4);
        System.arraycopy(mContent,0,bys,5,mContent.length);
        return bys;
    }


    public byte getmType() {
        return mType;
    }

    public void setMType(byte mType) {
        this.mType = mType;
    }

    public int getMLen() {
        return mLen;
    }

    public void setMLen(int mlen) {
        this.mLen = mlen;
    }

    public byte[] getmContent() {
        return mContent;
    }

    public void setMContent(byte[] mContent) {
        this.mContent = mContent;
    }
}
