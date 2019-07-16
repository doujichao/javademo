package spring.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service("asyncService")
public class AsyncServiceImpl  implements AsyncService{

    final Logger logger= LoggerFactory.getLogger(AsyncServiceImpl.class);


    @Async
    @Override
    public void asyncTask() {
        logger.info("Start execution of async. task");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            logger.error("Task interruption",e);
        }
        logger.info("Complete execution of async. task");
    }

    @Async
    @Override
    public Future<String> asyncWithReture(String name) {
        logger.info("Start execution of async. task with return for "+name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Task interruption",e);
        }
        logger.info("Complete execution of async. task with return for "+name);
        return new AsyncResult<>("hello: "+name);
    }
}

