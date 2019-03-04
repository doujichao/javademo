package util;


import java.io.*;

public class OSExecute {

    public static void command(String command) throws OSExecuteException {
        boolean err=false;
        try {
            Process process= new ProcessBuilder(command.split(" ")).start();
            BufferedReader results=new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
            String s;
            while ((s=results.readLine())!=null){
                s=new String(s.getBytes("gb2312"),"utf-8");
                System.out.println(s);
            }
            BufferedReader errors=new BufferedReader(new InputStreamReader(process.getErrorStream(),"utf-8"));
            while ((s=errors.readLine())!=null){
                System.out.println(s);
                err=true;
            }

        } catch (IOException e) {
            if (!command.startsWith("CMD /C")){
                command("CMD /C"+command);
            }else {
                throw new RuntimeException(e);
            }

        }
        if (err){
            throw new OSExecuteException("Errors executing"+command);
        }
    }

}
