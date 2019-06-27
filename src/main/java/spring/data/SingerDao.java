package spring.data;


import org.apache.hadoop.security.authentication.util.Signer;

import java.util.List;

public interface SingerDao {
    List<Singer> findAll();
    List<Singer> findByFirstName(String firstName);
    String findLastNameById(Long id);
    String findNameById(Long id);
    String findFirstNameById(Long id);
    void insert(Singer singer);
    void insertWithAlbum(Singer singer);
    void update(Singer singer);
    void delete(Long id);
    List<Singer> findAllWithDetail();
    void insertWithDetail(Singer singre);
    List<Singer> findAllWithAlbums();

}
