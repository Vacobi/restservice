package axiomatika.converter.jsonconverter.repository;

import axiomatika.converter.jsonconverter.entity.Xslt;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class XsltRepository extends CRUDRepository<Xslt, Long> {

    public XsltRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Xslt.class);
    }
}
