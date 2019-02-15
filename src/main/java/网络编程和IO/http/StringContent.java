package 网络编程和IO.http;

import 网络编程和IO.http.core.MalformedRequestException;
import 网络编程和IO.util.ChannelIO;

import java.io.IOException;

public class StringContent implements Content {
    public StringContent(Object e) {
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public long length() {
        return 0;
    }

    @Override
    public void prepare() throws IOException {

    }

    @Override
    public boolean send(ChannelIO cio) throws IOException {
        return false;
    }

    @Override
    public void release() throws IOException {

    }
}
