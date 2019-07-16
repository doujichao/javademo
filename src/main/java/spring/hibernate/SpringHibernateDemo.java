package spring.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import spring.AppConfig;
import spring.entities.Album;
import spring.entities.Instrument;
import spring.entities.Singer;

import java.util.*;

public class SpringHibernateDemo {
    private static  Logger logger= LoggerFactory.getLogger(SpringHibernateDemo.class);
    private GenericApplicationContext context;
    private SingerDao singerDao;
    private List<Singer> list=new ArrayList<>();

    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(AppConfig.class);
//        context=new GenericXmlApplicationContext("classpath:hibernate/hibernate-Application.xml");
        singerDao=context.getBean(SingerDao.class);
    }

    @Test
    public void testFindAll(){
        SingerDao bean = context.getBean(SingerDao.class);
        List<Singer> all = bean.findAllWithAlbum();
        listSinger(all);
    }

    @Test
    public void testFindById(){
        Singer bean = singerDao.findById(2l);
        list.add(bean);
        listSinger(list);
    }

    @Test
    public void testInsert(){
        Singer singer=new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(new Date(new GregorianCalendar
                (1940,8,16).getTime().getTime()));

        Album album=new Album();
        album.setTitle("My King of Blues");
        album.setReleaseDate(new Date(new GregorianCalendar(
                1961,7,18).getTime().getTime()));
        singer.addAlbum(album);

        album=new Album();
        album.setTitle("A heart Full of Bulue");
        album.setReleaseDate(new Date(new GregorianCalendar(
                1962,3,20).getTime().getTime()));
        singer.addAlbum(album);

        singerDao.save(singer);

        List<Singer> singers = singerDao.findAllWithAlbum();
        listSinger(singers);
    }

    @Test
    public void testUpdate(){
        Singer singer = singerDao.findById(1l);

        Album album1 = singer.getAlbums().stream().filter(album -> album.getTitle()
                .equals("Battle Studies")).findFirst().get();
        singer.setFirstName("Jonh Clayton");
        singer.removerAlbum(album1);
        singerDao.save(singer);
        listSinger(singerDao.findAllWithAlbum());
    }

    @Test
    public void testDelete(){
        Singer singer = singerDao.findById(1L);
        singerDao.delete(singer);
        listSinger(singerDao.findAllWithAlbum());
    }

    private static void listSinger(List<Singer> singers){
        logger.info("---- Listing singers:");
        if (singers==null){
            return;
        }
        for (Singer singer:singers){
            System.out.println(singer);
            Set<Album> albums = singer.getAlbums();
            if (albums instanceof Set && albums != null && albums.size()>=1){
                for (Album album:albums){
                    System.out.println("---"+album);
                }
            }
            Set<Instrument> instruments = singer.getInstruments();
            if (instruments instanceof Set &&instruments!=null && instruments.size()>=1){
                for (Instrument instrument:instruments){
                    System.out.println("------"+instrument);
                }
            }
        }
    }


    @After
    public void close(){
        context.close();
    }
}
