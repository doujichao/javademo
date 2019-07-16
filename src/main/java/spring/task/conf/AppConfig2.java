package spring.task.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import spring.task.service.AsyncService;
import spring.task.service.AsyncServiceImpl;
import spring.task.service.TaskToExecute;

@Configuration
@EnableAsync
public class AppConfig2 {

    @Bean
    public AsyncService asyncService(){
        return new AsyncServiceImpl();
    }

    @Bean
    public TaskToExecute taskToExecute(){
        return new TaskToExecute();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }
}
