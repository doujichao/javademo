package spring.task.conf;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import spring.task.service.AsyncService;
import spring.task.service.Carservice;
import spring.task.service.TaskToExecute;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ScheduleTaskDemo {

    final static Logger logger= LoggerFactory.getLogger(ScheduleTaskDemo.class);

    private GenericApplicationContext context;

    @Before
    public void before(){
//        context=new AnnotationConfigApplicationContext(AppConfig.class);
        context=new AnnotationConfigApplicationContext(AppConfig2.class);
    }

    @Test
    public void testTaskExecutor() throws IOException {
        TaskToExecute bean = context.getBean(TaskToExecute.class);
        bean.executeTask();
        System.in.read();

    }

    @After
    public void after(){
        context.close();
    }

    @Test
    public void testAsync() throws InterruptedException, ExecutionException, IOException {
        AsyncService asyncService = context.getBean("asyncService", AsyncService.class);
        for (int i=0;i<5;i++){
            asyncService.asyncTask();
        }

        Future<String> result1 = asyncService.asyncWithReture("John Mayer");
        Future<String> result2 = asyncService.asyncWithReture("Eric Clapton");
        Future<String> result3 = asyncService.asyncWithReture("BB King");
        Thread.sleep(6000);

        logger.info("Result1:"+result1.get());
        logger.info("Result2:"+result2.get());
        logger.info("Result3:"+result3.get());

        System.in.read();

    }

    @Test
    public void testTask() throws InterruptedException {
        Carservice carService = context.getBean("carService", Carservice.class);
        while (!carService.isDone()){
            logger.info("Wating for scheduled job to end ...");
            Thread.sleep(250);
        }
    }
}
