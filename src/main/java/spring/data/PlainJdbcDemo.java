package spring.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlainJdbcDemo {

    private static SingerDao singerDao=new PlainSingerDemo();
    private static Logger logger= LoggerFactory.getLogger(PlainJdbcDemo.class);

    public static void main(String[] args){
        logger.info("Listing initial singer data:");
        listAllSingers();

        logger.info("--------------");
        logger.info("Insert a new singer");
        Singer singer=new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new Date((new GregorianCalendar(1991,2,19)).getTime().getTime()));
        singerDao.insert(singer);

        logger.info("List singer datea after new singer created");
        listAllSingers();

        logger.info("---------------");
        logger.info("delete the previous created singer");
        singerDao.delete(singer.getId());

        logger.info("List singer datea after new singer deleted:");
        listAllSingers();
    }

    private static void listAllSingers() {
        List<Singer> all = singerDao.findAll();
        for (Singer singer:all){
            logger.info(singer.toString());
        }
    }
}
