package spring;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "spring.hibernate"
        ,excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableJpaRepositories(basePackages = "spring.hibernate")
public class JpaConfig {

    private static Logger logger= LoggerFactory.getLogger(JpaConfig.class);

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder dbBuilder=new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:db/h2/schema.sql"
                        ,"classpath:db/h2/test-data.sql")
                .build();
    }



    @Bean
    public PlatformTransactionManager transactionManager(){
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public Properties hibernateProperties(){
        logger.info("设定hibernate属性");
        Properties properties=new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.format_sql",true);
        properties.put("hibernate.user_sql_comments",true);
        properties.put("hibernate.show_sql",true);
        properties.put("hibernate.max_fetch_depth",3);
        properties.put("hibernate.java.jdbc.batch_size",10);
        properties.put("hibernate.java.jdbc.fetch_size",50);
        return properties;
    }


    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean=
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("spring.hibernate.entities");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        logger.info("开始初始化sessionFactory");
        LocalSessionFactoryBean sessionFactoryBean=new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("spring.hibernate.entities");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }
}
