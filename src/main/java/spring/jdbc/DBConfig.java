package spring.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import spring.data.SingerDao;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("classpath:db/jdbc2.properties")
public class DBConfig {

    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Lazy
    @Bean
    public DataSource dataSource(){
        try {
            SimpleDriverDataSource dataSource=new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Bean
    public DriverManagerDataSource dataSource1(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SingerDao singerDao(){
        SingerDao singerDao=new JdbcSingerDao();
        ((JdbcSingerDao) singerDao).setDataSource(dataSource());
        return singerDao;
    }
}
