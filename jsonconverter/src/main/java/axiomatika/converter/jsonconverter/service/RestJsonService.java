package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.entity.Json;
import axiomatika.converter.jsonconverter.entity.Xslt;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import axiomatika.converter.jsonconverter.repository.XsltRepository;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestJsonService {

    private final JsonRepository jsonRepository;
    private final XsltRepository xsltRepository;

    private final String SOAP_REQUEST_URI = "http://localhost:8081/ws/server";

    public RestJsonService(JsonRepository jsonRepository, XsltRepository xsltRepository) {
        this.jsonRepository = jsonRepository;
        this.xsltRepository = xsltRepository;
    }

    @Transactional
    public String convertToXslt(String json) {
        Json jsonEntity = jsonRepository.save(new Json(json));

        String xml = "<person>" + toXml(jsonEntity) + "</person>";

        String xslt = toXslt(xml);

        xsltRepository.save(new Xslt(xslt));

        return xslt;
    }

    private String toXml(Json json) {
        JSONObject jsonObject = new JSONObject(json.getContent());

        return XML.toString(jsonObject);
    }

    private String toXslt(String xml) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(SOAP_REQUEST_URI);
            request.setHeader("Content-Type", "text/xml; charset=UTF-8");
            request.setHeader("SOAPAction", SOAP_REQUEST_URI);
            request.setEntity(new StringEntity(buildSoapRequest(xml), "UTF-8"));
            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
            }
        } catch (Exception e) {
            throw new RuntimeException("SOAP service is unreachable", e);
        }
    }

    private String buildSoapRequest(String xml) {
        return String.format("""
        <?xml version="1.0" encoding="UTF-8"?>
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:axi="%s">
           <soapenv:Header/>
           <soapenv:Body>
              <axi:getXsltRequest>
                 <axi:xmlData><![CDATA[%s]]></axi:xmlData>
              </axi:getXsltRequest>
           </soapenv:Body>
        </soapenv:Envelope>
        """, SOAP_REQUEST_URI, xml);
    }

    private String decode(String encoded) {
        return StringEscapeUtils.unescapeXml(encoded);
    }

    private String extractBodyOfSoapResponse(String soapResponse) {
        JSONObject bodyOfResponseByObj = XML.toJSONObject(soapResponse);
        String bodyOfResponse;
        try {
            bodyOfResponse = bodyOfResponseByObj.getJSONObject("SOAP-ENV:Envelope")
                    .getJSONObject("SOAP-ENV:Body")
                    .getJSONObject("ns2:getXsltResponse")
                    .getString("ns2:xsltResult");
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }

        return bodyOfResponse;
    }
}

