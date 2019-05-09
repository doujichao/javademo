package 网络编程和IO.io流;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StreamTest {

    @Test
    public void testStream() throws IOException {
        String contents=new String(Files.readAllBytes(Paths.get("src","main","java","网络编程和IO","io流","alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split(" "));
        System.out.println(words);
        long count = words.stream().filter(w -> w.length()==0 ?true: w.charAt(w.length()-1 ) > 100).count();
        System.out.println(count);
        System.out.println((int) 'A');
    }
}
