package 网络编程和IO.https;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class SSLEngineDemo {
    private static boolean logging=true;
    private SSLContext sslContext;

    /**
     * 客户端引擎
     */
    private SSLEngine clientEngine;
    /**
     * 存放客户端发送的应用数据
     */
    private ByteBuffer clientOut;
    /**
     * 存放客户端接受到的应用数据
     */
    private ByteBuffer clientIn;

    /**
     * 服务器端引擎
     */
    private SSLEngine serverEngine;
    /**
     * 存放服务器端发送的应用数据
     */
    private ByteBuffer serverOut;
    /**
     * 存放服务器端接收到的应用数据
     */
    private ByteBuffer serverIn;

    /**
     * 存放客户端向服务器端发送的网路数据
     */
    private ByteBuffer cTOs;
    /**
     * 存放服务器端向客户端发送的网络数据
     */
    private ByteBuffer sTOs;


    /**
     * 设置密钥文件和信任库文件及口令
     */
    private static String keyStoreFile="test.key";
    private static String trusStoreFile="test.key";
    private static String passphrase="654321";

    public static void main(String[] args) throws Exception {
        SSLEngineDemo demo=new SSLEngineDemo();
        demo.runDemo();
        System.out.println("Demo Completed");
    }

    /**
     * 初始化SSLContext
     */
    public SSLEngineDemo() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");

        char[] password = passphrase.toCharArray();
        ks.load(new FileInputStream(keyStoreFile),password);
        ts.load(new FileInputStream(trusStoreFile),password);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks,password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        SSLContext sslContext1 = SSLContext.getInstance("TLS");
        sslContext1.init(kmf.getKeyManagers(),tmf.getTrustManagers(),null);
        sslContext=sslContext1;
    }

    private void runDemo() throws Exception {
        boolean dataDone=false;
        createSSLEngines();
        createBuffers();

        SSLEngineResult clientResult;
        SSLEngineResult serverResult;

        while (!isEngineClosed(clientEngine) || !isEngineClose(serverEngine)){
            log("===============================");
            //客户端打包应用数据
            clientResult=clientEngine.wrap(clientOut,cTOs);
            log("client wrap:",clientResult);

            //完成握手任务
            runDelegatedTasks(clientResult,clientEngine);
            //服务器端打包应用数据
            serverResult=serverEngine.wrap(serverOut,sTOs);

            log("server wrap:",serverResult);
            //完成握手任务
            runDelegatedTasks(serverResult,serverEngine);

            cTOs.flip();
            sTOs.flip();

            log("---");
            //客户端展开网络数据
            clientResult=clientEngine.unwrap(sTOs,clientIn);
            log("client unwrap：",clientResult);
            //完成握手任务
            runDelegatedTasks(clientResult,clientEngine);

            //服务器端展开网络数据
            serverResult=serverEngine.unwrap(cTOs,serverIn);
            log("server unwrap:",serverResult);
            //完成握手任务
            runDelegatedTasks(serverResult,serverEngine);

            cTOs.compact();
            sTOs.compact();

            if (!dataDone && (clientOut.limit() == serverIn.position())
            && serverOut.limit()== clientIn.position()){
                checkTransfer(serverOut,clientIn);
                checkTransfer(clientOut,serverIn);

                log("\tCloing clientEngine's *OUTBOUND*...");
                clientEngine.closeOutbound();
                dataDone=true;
            }
        }
    }

    /**
     * 判断两个缓冲区内容是否相同
     * @param a
     * @param b
     */
    private void checkTransfer(ByteBuffer a, ByteBuffer b) throws Exception {
        a.flip();
        b.flip();

        if (!a.equals(b)){
            throw new Exception("Data didn't transfer cleanly");
        }else {
            log("\tData transferred cleanly");
        }

        a.position(a.limit());
        b.position(b.limit());
        a.limit(a.capacity());
        b.limit(b.capacity());
    }


    private void runDelegatedTasks(SSLEngineResult result, SSLEngine engine) throws Exception {
        if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK){
            Runnable runnable;
            while ((runnable = engine.getDelegatedTask())!=null){
                log("\t running delegated task ...");
                runnable.run();
            }
            SSLEngineResult.HandshakeStatus hsStatus=engine.getHandshakeStatus();
            if (hsStatus == SSLEngineResult.HandshakeStatus.NEED_TASK){
                throw new Exception("handshake shouldn't need additional tasks");
            }
            log("\t new HandShakeStatus:"+hsStatus);
        }
    }

    private static boolean resultOnce=true;

    /**
     * 输出日志，打印SSLEngineResult的结果
     * @param s
     * @param clientResult
     */
    private void log(String s, SSLEngineResult clientResult) {
        if (resultOnce){
            resultOnce=false;
            System.out.println("The format of the SSLEngineResult is : \n"+"\t\"getStatus()/getHandShake" +
                    "Status()\"+\n"+"\t\"bytesConsumed()/bytesProduced()\"\n");
        }
        SSLEngineResult.HandshakeStatus hsStatus = clientResult.getHandshakeStatus();
        log(s+clientResult.getStatus()+"/"+hsStatus+",");
    }

    public void log(String ss){
        System.out.println(ss);
    }

    private boolean isEngineClose(SSLEngine serverEngine) {
        return false;
    }

    private boolean isEngineClosed(SSLEngine clientEngine) {
        return (clientEngine.isOutboundDone()&&clientEngine.isInboundDone());
    }

    private void createBuffers() {
        SSLSession session = clientEngine.getSession();
        int appBufferMax = session.getApplicationBufferSize();
        int netBufferMax = session.getPacketBufferSize();
        clientIn=ByteBuffer.allocate(appBufferMax+50);
        serverIn=ByteBuffer.allocate(appBufferMax+50);

        cTOs=ByteBuffer.allocate(netBufferMax);
        sTOs=ByteBuffer.allocate(netBufferMax);
        clientOut=ByteBuffer.wrap("Hi Serve ,I'm Client".getBytes());
        serverOut=ByteBuffer.wrap("Hello Client ,I'm Server".getBytes());
    }

    /**
     * 创建客户端及服务器端的SSEngine
     */
    private void createSSLEngines() {
        serverEngine=sslContext.createSSLEngine();
        serverEngine.setUseClientMode(false);

        serverEngine.setNeedClientAuth(true);
        clientEngine=sslContext.createSSLEngine("client",80);
        clientEngine.setUseClientMode(true);
    }
}
