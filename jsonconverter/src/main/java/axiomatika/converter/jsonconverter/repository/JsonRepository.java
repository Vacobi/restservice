package axiomatika.converter.jsonconverter.repository;

import axiomatika.converter.jsonconverter.entity.Json;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JsonRepository extends CRUDRepository<Json, Long> {

    public JsonRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Json.class);
    }
}
