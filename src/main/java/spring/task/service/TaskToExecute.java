package spring.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

public class TaskToExecute {

    private Logger logger= LoggerFactory.getLogger(TaskToExecute.class);

    @Autowired
    private TaskExecutor taskExecutor;

    public void executeTask(){
        for (int i=0;i<10;i++){
            taskExecutor.execute(()->{
                logger.info("Hellow from thread:"+Thread.currentThread().getName());
            });
        }
    }
}
