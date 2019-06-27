package spring.jdbc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.data.Album;
import spring.data.Singer;
import spring.data.SingerDao;
import spring.jdbc.batch.InsertSinger;
import spring.jdbc.batch.InsertSingerAlbum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("singerDao")
public class JdbcSingerDao implements SingerDao, InitializingBean {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SelectAllSingers selectAllSingers;
    private InsertSingerAlbum insertSingerAlbum;
    private InsertSinger insertSinger;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public String findNameById(Long id){
        String sql="Select first_name || ' ' || last_name from singer where id = :singerId";
        Map<String,Object> namedParameters=new HashMap<>();
        namedParameters.put("singerId",id);
        return namedParameterJdbcTemplate.queryForObject(sql,namedParameters,String.class);
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource=dataSource;
        jdbcTemplate=new JdbcTemplate();
        insertSinger=new InsertSinger(dataSource);
        selectAllSingers=new SelectAllSingers(dataSource);
        jdbcTemplate.setDataSource(dataSource);
        insertSingerAlbum=new InsertSingerAlbum(dataSource);
    }

    @Override
    public void afterPropertiesSet() {
        if(namedParameterJdbcTemplate==null)
            namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<Singer> findAll() {
        return namedParameterJdbcTemplate.query("select id,first_name,last_name,birth_date" +
                " from singer",new SingerMapper());
    }

    private class SingerMapper implements RowMapper<Singer> {

        @Override
        public Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Singer singer=new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;
        }
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return jdbcTemplate.queryForObject("select first_name || ' ' || last_name from singer where id = ?"
                ,new Object[]{id},String.class);
    }


    private StoreFunctionFirstNameById function;

    @Override
    public String findFirstNameById(Long id) {
        function=new StoreFunctionFirstNameById(dataSource);
        List<String> result = function.execute(id);
        return result.get(0);
    }

    @Override
    public void insert(Singer singer) {
        throw new NotImplementedException();
    }

    @Override
    public void insertWithAlbum(Singer singer) {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("first_name",singer.getFirstName());
        paramMap.put("last_name",singer.getLastName());
        paramMap.put("birth_date",singer.getBirthDate());

        KeyHolder keyHolder=new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(paramMap,keyHolder);
        singer.setId(keyHolder.getKey().longValue());

        List<Album> albums=singer.getAlbums();
        if (albums!=null){
            for (Album album:albums){
                paramMap=new HashMap<>();
                paramMap.put("singer_id",singer.getId());
                paramMap.put("title",album.getTitle());
                paramMap.put("release_date",album.getReleaseDate());
                insertSingerAlbum.updateByNamedParam(paramMap);
            }
        }
    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singre) {

    }

    public List<Singer> findAllWithAlbums(){
        String sql="select s.id,s.first_name,s.last_name,s.birth_date,a.id album_id,a.title,a.release_date from singer s " +
                "left join album a on s.id=a.singer_id";
        return namedParameterJdbcTemplate.query(sql,new SimgerWithDetailExtractor());
    }

    private class SimgerWithDetailExtractor implements ResultSetExtractor<List<Singer>>{
        @Override
        public List<Singer> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long,Singer> map=new HashMap<>();
            Singer singer;
            while (rs.next()){
                long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null){
                    singer=new Singer();
                    singer.setId(id);
                    singer.setFirstName(rs.getString("first_name"));
                    singer.setLastName(rs.getString("last_name"));
                    singer.setBirthDate(rs.getDate("birth_date"));
                    singer.setAlbums(new ArrayList<>());
                    map.put(id,singer);
                }
                long albumId = rs.getLong("album_id");
                if (albumId>0){
                    Album album=new Album();
                    album.setId(albumId);
                    album.setSingerId(id);
                    album.setTitle(rs.getString("title"));
                    album.setReleaseDate(rs.getDate("release_date"));
                    singer.addAlum(album);
                }
            }
            return new ArrayList<>(map.values());
        }
    }
}
