package spring.remote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import spring.remote.service.SingerService;

@Configuration
public class RmiClientConfig {

    @Bean
    public SingerService singerService(){
        HttpInvokerProxyFactoryBean factoryBean=
                new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(SingerService.class);
        factoryBean.setServiceUrl("http://localhost:808/invoker/httpInvoker/singerSerice");
        factoryBean.afterPropertiesSet();
        return (SingerService) factoryBean.getObject();
    }

}
