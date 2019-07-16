package spring.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import spring.JpaConfig;
import spring.entities.Album;
import spring.entities.Singer;
import spring.entities.SingerSummary;

import java.util.Date;
import java.util.List;


public class SpringJPATest {

    private static Logger logger= LoggerFactory.getLogger(SpringJPATest.class);
    private GenericApplicationContext ctx;
    private SingerService singerService;
    SingerSummaryUntypeImpl singerSummaryUntype ;
    private SingerSummaryService singerSummaryService;

    @Before
    public void before(){
        ctx=new AnnotationConfigApplicationContext(JpaConfig.class);
//        ctx=new GenericXmlApplicationContext("classpath:jpa/jpa-Application.xml");
        singerService=ctx.getBean(SingerService.class);
        singerSummaryUntype = ctx.getBean(SingerSummaryUntypeImpl.class);
        singerSummaryService=ctx.getBean(SingerSummaryService.class);
    }

    @Test
    public void testFindAllService(){
        List<SingerSummary> all = singerSummaryService.findAll();
        logger.info("----Listing singer summary");
        all.forEach(singerSummary -> {
            System.err.println(singerSummary);
        });
    }

    @Test
    public void testInsert(){
        Singer singer=new Singer();
        singer.setFirstName("Bb");
        singer.setLastName("ccc");
        singer.setBirthDate(new Date());

        Album album=new Album();
        album.setTitle("aaaaaa");
        album.setReleaseDate(new Date());
        singer.addAlbum(album);
        album=new Album();
        album.setTitle("vvvvvvv");
        album.setReleaseDate(new Date());
        singer.addAlbum(album);

        singerService.save(singer);
        listStringers(singerService.findAllWithAlbum());
    }

    @Test
    public void testFindAll(){
        List<Singer> singers = singerService.findAll();
        listStringers(singers);
    }

    @Test
    public void testFindByCriteriaQuery(){
        List<Singer> singers = singerService.findByCriteriaQuery("John", "Mayer");
        listStringers(singers);
    }

    @Test
    public void testfindAllByNativeQuery(){
        List<Singer> singers = singerService.findAllByNativeQuery();
        listOnlyStringers(singers);
    }

    private void listOnlyStringers(List<Singer> singers) {
        singers.forEach(singer -> {
            System.err.println(singer);
        });
    }


    @Test
    public void testFindAllWithAlbum(){
        List<Singer> singers = singerService.findAllWithAlbum();
        listStringers(singers);
    }

    @Test
    public void testFindAllUntype(){
        singerSummaryUntype.displayAllSingerSummary();
    }

    private void listStringers(List<Singer> singers) {
        singers.forEach(singer -> {
            System.err.println(singer);
            if (singer.getAlbums() != null) {
                for (Album album : singer.getAlbums()) {
                    System.err.println("------" + album);
                }
            }
        });
    }

    @After
    public void after(){
        ctx.close();
    }
}
