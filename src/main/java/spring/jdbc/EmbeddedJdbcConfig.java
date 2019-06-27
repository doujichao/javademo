package spring.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import spring.data.SingerDao;

import javax.sql.DataSource;

@Configuration
public class EmbeddedJdbcConfig {

    private static Logger logger= LoggerFactory.getLogger(EmbeddedJdbcConfig.class);

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder dbBuilder=new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:db/h2/schema.sql","classpath:db/h2/test-data.sql")
                .build();
    }


    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao dao=new JdbcSingerDao();
        dao.setDataSource(dataSource());
//        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }

}
