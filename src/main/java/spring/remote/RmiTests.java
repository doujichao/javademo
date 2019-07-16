package spring.remote;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import spring.entities.Singer;
import spring.remote.config.RmiClientConfig;
import spring.remote.service.SingerService;

import java.util.List;

@ContextConfiguration(classes = RmiClientConfig.class)
@RunWith(SpringRunner.class)
public class RmiTests {

    @Autowired
    private SingerService singerService;

    @Test
    public void testRmiAll(){
        List<Singer> singers = singerService.findAll();
        listSingers(singers);
    }

    private void listSingers(List<Singer> singers) {
        singers.forEach(singer -> System.out.println(singer));
    }

}
