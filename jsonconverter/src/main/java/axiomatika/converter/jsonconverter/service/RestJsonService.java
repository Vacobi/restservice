package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.builder.SoapMessagesBuilder;
import axiomatika.converter.jsonconverter.dto.ConvertToXsltResult;
import axiomatika.converter.jsonconverter.entity.Json;
import axiomatika.converter.jsonconverter.entity.Xslt;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import axiomatika.converter.jsonconverter.repository.XsltRepository;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestJsonService {

    private final JsonRepository jsonRepository;
    private final XsltRepository xsltRepository;

    private final SoapMessagesBuilder soapMessagesBuilder;

    public RestJsonService(JsonRepository jsonRepository,
                           XsltRepository xsltRepository,
                           SoapMessagesBuilder soapMessagesBuilder) {
        this.jsonRepository = jsonRepository;
        this.xsltRepository = xsltRepository;
        this.soapMessagesBuilder = soapMessagesBuilder;
    }

    @Transactional
    public ConvertToXsltResult convertToXslt(String json) {
        ConvertToXsltResult result = new ConvertToXsltResult();

        Json jsonEntity = jsonRepository.save(new Json(json));
        result.setJsonId(jsonEntity.getId());

        String xml = "<person>" + toXml(jsonEntity) + "</person>";

        String xslt = toXslt(xml);

        Xslt xsltEntity = xsltRepository.save(new Xslt(xslt));
        result.setXsltId(xsltEntity.getId());
        result.setResult(xslt);

        return result;
    }

    private String toXml(Json json) {
        JSONObject jsonObject = new JSONObject(json.getContent());

        return XML.toString(jsonObject);
    }

    private String toXslt(String xml) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = soapMessagesBuilder.buildXMLSoapRequest(xml);
            try (CloseableHttpResponse response = client.execute(request)) {
                String codedBodyOfSoapResponse = soapMessagesBuilder.extractBodyOfSoapResponse(response);
                return codedBodyOfSoapResponse == null ? null : decode(codedBodyOfSoapResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException("SOAP service is unreachable", e);
        }
    }

    private String decode(String encoded) {
        return StringEscapeUtils.unescapeXml(encoded);
    }
}

