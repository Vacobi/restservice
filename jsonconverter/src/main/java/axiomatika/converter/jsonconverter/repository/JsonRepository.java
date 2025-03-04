package axiomatika.converter.jsonconverter.repository;

import axiomatika.converter.jsonconverter.entity.JsonEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JsonRepository extends CRUDRepository<JsonEntity, Long> {

    public JsonRepository(SessionFactory sessionFactory) {
        super(sessionFactory, JsonEntity.class);
    }
}
