package spring.hibernate;

import spring.hibernate.entities.Singer;

import java.util.List;

public interface SingerService {

    List<Singer> findAll();
    List<Singer> findByFirstName(String firstName);
    List<Singer> findByFirstNameAndLastName(String firstName,String lastName);
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer contact);
    void delete(Singer contact);
    List<Singer> findAllByNativeQuery();
    List<Singer> findByCriteriaQuery(String firstName,String lastName);
}
