package spring.prop;

import org.springframework.core.convert.converter.Converter;

public class SingerToAnotherSingerConverter implements Converter<Singer, AnotherSinger> {
    @Override
    public AnotherSinger convert(Singer source) {
        AnotherSinger anotherSinger=new AnotherSinger();
        anotherSinger.setFirstName(source.getFirstName());
        anotherSinger.setLastName(source.getLastName());
        anotherSinger.setBirthDate(source.getBirthDate());
        anotherSinger.setPersonalSite(source.getPersonalSite());
        return anotherSinger;
    }
}
