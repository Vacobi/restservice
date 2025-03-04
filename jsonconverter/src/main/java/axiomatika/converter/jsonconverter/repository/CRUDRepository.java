package axiomatika.converter.jsonconverter.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class CRUDRepository<E, ID extends Serializable> {

    private final SessionFactory sessionFactory;
    private final Class<E> entityClass;

    public CRUDRepository(SessionFactory sessionFactory, Class<E> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Transactional
    public E save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(entity);
    }

    @Transactional
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Transactional
    public Optional<E> findById(ID id) {
        Session session = sessionFactory.getCurrentSession();
        E entity = session.get(entityClass, id);
        return entity == null ? Optional.empty() : Optional.of(entity);
    }

    @Transactional
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<E> query = builder.createQuery(entityClass);
        Root<E> root = query.from(entityClass);
        query.select(root);

        return session.createQuery(query).getResultList();
    }
}
