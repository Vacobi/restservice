package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.entity.Json;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import axiomatika.converter.jsonconverter.repository.XsltRepository;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestJsonService {

    private final JsonRepository jsonRepository;
    private final XsltRepository xsltRepository;

    public RestJsonService(JsonRepository jsonRepository, XsltRepository xsltRepository) {
        this.jsonRepository = jsonRepository;
        this.xsltRepository = xsltRepository;
    }

    @Transactional
    public String convertToXslt(String json) {
        Json jsonEntity = jsonRepository.save(new Json(json));

        String xml = "<person>" + toXml(jsonEntity) + "</person>";

        return xml;
    }

    private String toXml(Json json) {
        JSONObject jsonObject = new JSONObject(json.getContent());

        return XML.toString(jsonObject);
    }

    private String toXslt(String xml) {
        return "";
    }
}
