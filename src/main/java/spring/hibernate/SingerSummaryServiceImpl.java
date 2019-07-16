package spring.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entities.SingerSummary;

import javax.persistence.EntityManager;
import java.util.List;

@Service("singerSummaryService")
@Repository
@Transactional
public class SingerSummaryServiceImpl implements SingerSummaryService {

    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<SingerSummary> findAll() {
        List result = entityManager.createQuery("" +
                "select new spring.hibernate.entities.SingerSummary (" +
                "s.firstName,s.lastName,a.title) from Singer s " +
                "left join s.albums a " +
                "where a.releaseDate=(select max(a2.releaseDate) " +
                "from Album a2 where a2.singer.id=s.id)").getResultList();
        return result;
    }
}
