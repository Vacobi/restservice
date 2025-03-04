package axiomatika.converter.jsonconverter.repository;

import axiomatika.converter.jsonconverter.entity.XsltEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class XsltRepository extends CRUDRepository<XsltEntity, Long> {

    public XsltRepository(SessionFactory sessionFactory) {
        super(sessionFactory, XsltEntity.class);
    }
}
