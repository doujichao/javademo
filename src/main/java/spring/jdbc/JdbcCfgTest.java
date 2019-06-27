package spring.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import spring.data.Album;
import spring.data.Singer;
import spring.data.SingerDao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JdbcCfgTest {

    private ConfigurableApplicationContext context;
    private SingerDao singerDao;
    @Before
    public void before(){
        context=new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
//        context=new AnnotationConfigApplicationContext(DBConfig.class);
        singerDao=context.getBean(SingerDao.class);
    }

    @Test
    public void testFindFirstNamyById(){
        String firstName = singerDao.findFirstNameById(2l);
        System.out.println("Retrieved value:"+firstName);
    }

    @After
    public void tearDown(){
        context.close();
    }
    @Test
    public void testSingerInsertWithAlbum(){
        Singer singer=new Singer();
        singer.setFirstName("bb");
        singer.setLastName("King");
        singer.setBirthDate(new Date(new GregorianCalendar(1940,8,18).getTime().getTime()));

        Album album=new Album();
        album.setTitle("My King of Blues");
        album.setReleaseDate(new Date(new GregorianCalendar(1961,7,18).getTime().getTime()));
        singer.addAlum(album);

        album=new Album();
        album.setTitle("A heart full of blues");
        album.setReleaseDate(new Date(new GregorianCalendar(1962,3,20).getTime().getTime()));
        singer.addAlum(album);

        SingerDao singerDao = context.getBean(SingerDao.class);
        singerDao.insertWithAlbum(singer);

        List<Singer> singers = singerDao.findAllWithAlbums();
        listStringers(singers);
    }


    @Test
    public void testResultSetExtractor(){
        SingerDao singerDao = context.getBean(SingerDao.class);
        List<Singer> singers = singerDao.findAllWithAlbums();
        listStringers(singers);
    }

    private void listStringers(List<Singer> singers) {
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null) {
                for (Album album : singer.getAlbums()) {
                    System.out.println("------" + album);
                }
            }
        });
    }

    @Test
    public void testRowMapper(){
        SingerDao bean = context.getBean(SingerDao.class);
        List<Singer> all = bean.findAll();

        listStringers(all);
    }

    @Test
    public void testH2DB(){
        GenericXmlApplicationContext context=new GenericXmlApplicationContext(
                "classpath:db/dbApplication.xml");
        testDao(context.getBean(SingerDao.class));
        context.close();
    }

    private void testDao(SingerDao bean) {
        String singerName = bean.findLastNameById(1l);
        System.out.println(singerName);
        assert "John Mayer".equals(singerName);
    }

    @Test
    public void testJavaConf(){
        testDao(context.getBean(SingerDao.class));
        context.close();
    }
}
