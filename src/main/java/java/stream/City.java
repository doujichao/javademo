package java.stream;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class City {

    private String name;
    private String state;
    private int population;

    public City(String name, String state, int population) {
        this.name = name;
        this.state = state;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public static Stream<City> readCities(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).map(l->l.split(","))
                .map(a->new City(a[0],a[1],Integer.parseInt(a[2])));
    }

    public static void main(String[] args){
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<Locale>> countryToLocalSet = locales.collect(groupingBy(Locale::getCountry, toSet()));
        System.out.println("countryToLocalSet:"+countryToLocalSet);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Long> countryToLocalCounts = locales.collect(groupingBy(Locale::getCountry, counting()));
        System.out.println("countryToLocalCounts:"+countryToLocalCounts);


    }
}
