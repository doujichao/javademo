package java.stream;

import util.MyConstants;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建Stream的测试类
 */
public class CreatingStream {


    /**
     * 展示Stream
     * @param title 类别
     * @param stream 流
     * @param <T>
     */
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE=10;
        List<T> firstElements = stream.limit(SIZE + 1)
                .collect(Collectors.toList());
        System.out.println(title+":");
        for (int i=0;i<firstElements.size();i++){
            if (i>0) System.out.print(", ");
            if (i< SIZE) System.out.print(firstElements.get(i));
            else System.out.print("...");
        }
        System.out.println();

    }


    public static void main(String[] args) throws IOException {
        Path path = Paths.get(MyConstants.BASE_FILEPATH+ "java/stream/alice30.txt");
        byte[] bytes = Files.readAllBytes(path);
        String contents=new String(bytes, StandardCharsets.UTF_8);
        Stream<String> words  = Stream.of(contents.split(" "));
        show("words",words);
        Stream<String> song = Stream.of("gently", "down", "the", "java/stream");
        show("song",song);
        Stream<Object> silence = Stream.empty();
        show("silence",silence);

        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos",echos);

        Stream<Double> random = Stream.generate(() -> Math.random());
        show("random",random);

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE));
        show("integers",integers);

        Stream<String> wordsAnotherWay = Pattern.compile(" ").splitAsStream(contents);
        show("wordsAnotherWay",wordsAnotherWay);
    }
}
