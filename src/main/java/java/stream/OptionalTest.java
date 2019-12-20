package java.stream;

import util.MyConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) throws IOException {
        String constants = new String(Files.readAllBytes(Paths.get(MyConstants.BASE_FILEPATH + "java/stream/alice30.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(constants.split(" "));
        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("a")).findFirst();
        System.out.println(optionalValue.orElse("No word")+" contains a");

        Optional<String> optionalString = Optional.empty();
        String result =  optionalString.orElse("N/A");
        System.out.println("result:"+result);
        result=optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result:"+result);
        try {
//            result=optionalString.orElseThrow(IllegalStateException::new);
            result=optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("result:"+result);
        }catch (Throwable t){
            t.printStackTrace();
        }

    }
}
