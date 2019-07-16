package spring.task.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import({DataServiceConfig.class})
@ImportResource("classpath:task/application.xml")
public class AppConfig {
}
