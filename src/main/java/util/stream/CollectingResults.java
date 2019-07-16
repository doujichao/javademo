package util.stream;

import util.MyConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingResults {

    public static Stream<String> noVowels() throws IOException {
        String constants = new String(Files
                .readAllBytes(Paths
                        .get(MyConstants.BASE_FILEPATH
                                + "util/stream/alice30.txt")),
                StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(constants.split(" "));
        Stream<String> words = wordList.stream();
        return words.map(s->s.replaceAll("[abcdefg]","h"));
    }

    public static <T> void  show(String label, Set<T> set){
        System.out.println(label+":"+set.getClass().getName());
        System.out.println("["+set.stream().limit(10).map(Objects::toString)
        .collect(Collectors.joining(", "))+"]");
    }

    public static void main(String[] args) throws IOException {
        Iterator<Integer> iterator = Stream.iterate(0, n -> n + 2).limit(10).iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }

        System.out.println();
        Object[] numbers = Stream.iterate(1, n -> n + 1).limit(10).toArray();
        System.out.println("Object array:"+Arrays.toString(numbers));

        try {
            Integer number= (Integer) numbers[0];
            System.out.println("number:"+number);
            System.out.println("The following statement throws an test.exception:");
            Integer[] numbers2= (Integer[]) numbers;
        }catch (ClassCastException e){
            System.out.println(e);
        } 

        Integer[] numbers3 = Stream.iterate(0, integer -> integer + 1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array:"+Arrays.toString(numbers3));

        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet",noVowelSet);

        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet",noVowelTreeSet);

        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining:"+result);

        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("Joining with commas:"+result);

        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        int max = summary.getMax();
        System.out.println("Average word length:"+averageWordLength);
        System.out.println("Max word length:"+max);
        System.out.println("forEach:");
        noVowels().limit(10).forEach(System.out::println);
    }
}
