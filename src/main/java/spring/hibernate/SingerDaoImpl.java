package spring.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.entities.Singer;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Singer").list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAllWithAlbum() {
        return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum").list();
    }

    @Override
    public Singer findById(Long id) {
        return (Singer) sessionFactory.getCurrentSession()
                .getNamedQuery("Singer.findById").setParameter("id",id)
                .uniqueResult();
    }

    @Override
    public Singer save(Singer contact) {
        sessionFactory.getCurrentSession().saveOrUpdate(contact);
        return contact;
    }

    @Override
    public void delete(Singer contact) {
        sessionFactory.getCurrentSession().delete(contact);
    }
}
