package spring.task.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({DataServiceConfig.class})
@EnableScheduling
public class AppConfig1 {
}
