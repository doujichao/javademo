package spring.remote.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "spring.remote.repos")
@ComponentScan(basePackages = {"spring.remote"})
public class DataServiceConfig {

    private static Logger logger= LoggerFactory.getLogger(DataServiceConfig.class);

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder databaseBuilder=new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public Properties hibernateProperties(){
        logger.info("设定hibernate属性");
        Properties properties=new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto","create-drop");
        properties.put("hibernate.format_sql",true);
        properties.put("hibernate.user_sql_comments",true);
        properties.put("hibernate.show_sql",true);
        properties.put("hibernate.max_fetch_depth",3);
        properties.put("hibernate.jdbc.batch_size",10);
        properties.put("hibernate.jdbc.fetch_size",50);
        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean=new
                LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("string.remote.entities");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new JpaTransactionManager(entityManagerFactory());
    }
}
