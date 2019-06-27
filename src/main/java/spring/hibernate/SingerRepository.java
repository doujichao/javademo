package spring.hibernate;

import org.springframework.data.repository.CrudRepository;
import spring.hibernate.entities.Singer;

import java.util.List;

public interface SingerRepository extends CrudRepository<Singer,Long> {
    List<Singer> findByFirstName(String firstName);
    List<Singer> findByFirstNameAndLastName(String firstName,String lastName);
}
