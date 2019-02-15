package 网络编程和IO.http;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Handler {
    void handle(SelectionKey key) throws IOException;
}
