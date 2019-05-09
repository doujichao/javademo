package stream;

import util.MyConstants;

import static java.util.stream.Collectors.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ParallelStreams {

    public static void main(String[] args) throws IOException {
        String contents=new String(Files.readAllBytes(Paths.get(MyConstants.BASE_FILEPATH
                +"stream/alice30.txt")));
        List<String> wordList = Arrays.asList(contents.split(" "));

        int[] shortWords=new int[10];
        wordList.parallelStream().forEach(s -> {
            if (s.length()<10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        //try again -the result will likely be different(and also wrong )
        Arrays.fill(shortWords,0);
        wordList.parallelStream().forEach(s -> {
            if (s.length()<10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        //Remedy :group and count
        Map<Integer, Long> shortWordCounts = wordList.parallelStream().filter(s -> s.length() < 10)
                .collect(groupingBy(String::length, counting()));
        System.out.println(shortWordCounts);

        //DownStream order not deterministic
        ConcurrentMap<Integer, List<String>> result = wordList.parallelStream().collect(Collectors.groupingByConcurrent(String::length));
        System.out.println(result.get(4));


    }
}
