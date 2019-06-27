package spring.prop.conf;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import spring.prop.Singer;
import spring.prop.SingerToAnotherSingerConverter;
import spring.prop.convert.ApplicationConversionServiceFactoryBean;
import spring.prop.convert.StringToDateTimeConverter;

import java.net.URL;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Configuration
@PropertySource("classpath:format/format.properties")
@ComponentScan(basePackages = "spring.prop.convert")
public class AppConfig {

    @Value("${date.format.pattern}")
    private String dateFormatPattern;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
        propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private ApplicationConversionServiceFactoryBean conversionService;

    @Bean
    public Singer john(@Value("${countrySinger.firstName}") String firstName,
                       @Value("${countrySinger.lastName}") String lastName,
                       @Value("${countrySinger.personalSite}") URL personalSite,
                       @Value("${countrySinger.birthDate}") DateTime birthDate) throws ParseException {
        Singer singer=new Singer();
        singer.setFirstName(firstName);
        singer.setLastName(lastName);
        singer.setBirthDate(conversionService.getDateTimeFormatter()
                .parse(birthDate.toString("yyyy-MM-dd"), Locale.ENGLISH));
        singer.setPersonalSite(personalSite);
        return singer;
    }


    @Bean
    public ConversionServiceFactoryBean conversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean=new ConversionServiceFactoryBean();
        Set<Converter> converters=new HashSet<>();
        converters.add(converter());
        converters.add(singerConverter());
        conversionServiceFactoryBean.setConverters(converters);
        conversionServiceFactoryBean.afterPropertiesSet();
        return conversionServiceFactoryBean;
    }

    @Bean
    public SingerToAnotherSingerConverter singerConverter(){
        return new SingerToAnotherSingerConverter();
    }

    @Bean
    public StringToDateTimeConverter converter() {
        StringToDateTimeConverter conv=new StringToDateTimeConverter();
        conv.setDatePattern(dateFormatPattern);
        conv.inti();
        return conv;
    }

    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }
}
