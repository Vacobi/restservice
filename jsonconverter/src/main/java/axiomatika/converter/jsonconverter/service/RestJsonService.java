package axiomatika.converter.jsonconverter.service;

import axiomatika.converter.jsonconverter.builder.SoapMessagesHandler;
import axiomatika.converter.jsonconverter.dto.ConvertRequestDto;
import axiomatika.converter.jsonconverter.dto.ConvertToXsltResult;
import axiomatika.converter.jsonconverter.entity.JsonEntity;
import axiomatika.converter.jsonconverter.entity.XsltEntity;
import axiomatika.converter.jsonconverter.exception.IncorrectJsonException;
import axiomatika.converter.jsonconverter.repository.JsonRepository;
import axiomatika.converter.jsonconverter.repository.XsltRepository;
import axiomatika.converter.jsonconverter.validation.JsonValidator;
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

    private final SoapMessagesHandler soapMessagesHandler;
    private final JsonValidator jsonValidator;

    public RestJsonService(JsonRepository jsonRepository,
                           XsltRepository xsltRepository,
                           SoapMessagesHandler soapMessagesHandler,
                           JsonValidator jsonValidator) {
        this.jsonRepository = jsonRepository;
        this.xsltRepository = xsltRepository;
        this.soapMessagesHandler = soapMessagesHandler;
        this.jsonValidator = jsonValidator;
    }

    @Transactional
    public ConvertToXsltResult convertToXslt(ConvertRequestDto convertRequestDto) {
        jsonValidator.validateConvertRequestDto(convertRequestDto).ifPresent(e -> {
            throw e;
        });

        String json = convertRequestDto.getData();
        ConvertToXsltResult result = new ConvertToXsltResult();

        JsonEntity jsonEntity = jsonRepository.save(new JsonEntity(json));
        result.setJsonId(jsonEntity.getId());

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonEntity.getContent());
        }
        catch (Exception e) {
            throw new IncorrectJsonException(json);
        }
        String xml = String.format("<person> %s </person>", XML.toString(jsonObject));

        String xslt = toXslt(xml);

        XsltEntity xsltEntity = xsltRepository.save(new XsltEntity(xslt));
        result.setXsltId(xsltEntity.getId());
        result.setResult(xslt);

        return result;
    }

    private String toXslt(String xml) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = soapMessagesHandler.buildXMLSoapRequest(xml);
            try (CloseableHttpResponse response = client.execute(request)) {
                String codedBodyOfSoapResponse = soapMessagesHandler.extractBodyOfSoapResponse(response);
                return codedBodyOfSoapResponse == null ? null : StringEscapeUtils.unescapeXml(codedBodyOfSoapResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException("SOAP service is unreachable", e);
        }
    }
}

