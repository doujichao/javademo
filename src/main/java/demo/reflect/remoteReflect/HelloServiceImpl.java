package demo.reflect.remoteReflect;

import java.util.Date;

public class HelloServiceImpl implements HelloServer {
    @Override
    public String echo(String msg) {
        return "echo:"+msg;
    }

    @Override
    public Date getTime() {
        return new Date();
    }
}
