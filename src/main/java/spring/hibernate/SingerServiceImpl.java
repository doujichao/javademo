package spring.hibernate;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.entities.Singer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Service("jpaSingerService")
@Repository
public class SingerServiceImpl implements SingerService {

    final static String ALL_SINGER_NATIVE_QUERY=
            "select id,first_name,last_name,birth_date,version from singer";

    private static Logger logger= LoggerFactory.getLogger(SingerServiceImpl.class);

    @PersistenceContext(name = "entityManagerFactory")
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll()
    {

       return entityManager.createNamedQuery(Singer.FIND_ALL,Singer.class).getResultList();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        throw new NotImplementedException("findByFirstName");
    }

    @Override
    public List<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        throw new NotImplementedException("findByFirstNameAndLastName");
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAllWithAlbum() {
        return entityManager.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM,Singer.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Singer findById(Long id) {
        throw new NotImplementedException("findById");
    }

    @Transactional(readOnly = true)
    @Override
    public Singer save(Singer contact) {
        if (contact.getId()==null){
            logger.info("Inserting new singer");
            entityManager.persist(contact);
        }else {
            entityManager.merge(contact);
            logger.info("Updating existing singer");
        }
        logger.info("Singer saved with id:"+contact.getId());
        return contact;
    }

    @Transactional(readOnly = true)
    @Override
    public void delete(Singer contact) {
        throw new NotImplementedException("delete");
    }
    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAllByNativeQuery() {
//       return entityManager.createNativeQuery(ALL_SINGER_NATIVE_QUERY,Singer.class).getResultList();
       return entityManager.createNativeQuery(ALL_SINGER_NATIVE_QUERY,"singerResult").getResultList();
    }

    @Override
    public List<Singer> findByCriteriaQuery(String firstName, String lastName) {
        logger.info("Finding singer for firstName:"+firstName+" and lastName:"+lastName);
        //创建类型化查询构建类型
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        //创建类型化查询
        CriteriaQuery<Singer> criteriaQuery = cb.createQuery(Singer.class);
        //传入根对象，获取查询根
        Root<Singer> singerRoot = criteriaQuery.from(Singer.class);
        singerRoot.fetch(Singer_.albums, JoinType.LEFT);
        singerRoot.fetch(Singer_.instruments,JoinType.LEFT);

        criteriaQuery.select(singerRoot).distinct(true);
        Predicate criteria = cb.conjunction();
        if (firstName!=null){
            Predicate p = cb.equal(singerRoot.get(Singer_.firstName), firstName);
            criteria=cb.and(criteria,p);
        }

        if (lastName!=null){
            Predicate p = cb.equal(singerRoot.get(Singer_.lastName), lastName);
            criteria=cb.and(criteria,p);
        }
        criteriaQuery.where(criteria);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
