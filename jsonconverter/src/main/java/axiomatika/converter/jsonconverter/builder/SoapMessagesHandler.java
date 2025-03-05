package axiomatika.converter.jsonconverter.builder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SoapMessagesHandler {

    private final String SOAP_REQUEST_URI;
    private final String charset;
    private final String soapRequestLocalName;
    private final String elementName;
    private final String soapResponseLocalName;
    private final String responseElementName;

    public SoapMessagesHandler(@Qualifier("soapRequestUri") String SOAP_REQUEST_URI,
                               @Qualifier("charset") String charset,
                               @Qualifier("soapRequestLocalName") String soapRequestLocalName,
                               @Qualifier("elementName") String elementName,
                               @Qualifier("soapResponseLocalName") String soapResponseLocalName,
                               @Qualifier("responseElementName") String responseElementName) {

        this.SOAP_REQUEST_URI = SOAP_REQUEST_URI;
        this.charset = charset;
        this.soapRequestLocalName = soapRequestLocalName;
        this.elementName = elementName;
        this.soapResponseLocalName = soapResponseLocalName;
        this.responseElementName = responseElementName;
    }

    public HttpPost buildXMLSoapRequest(String xml) {
        HttpPost request = new HttpPost(SOAP_REQUEST_URI);
        request.setHeader("Content-Type", String.format("text/xml; charset=%s", charset));
        request.setHeader("SOAPAction", SOAP_REQUEST_URI);
        request.setEntity(new StringEntity(buildSoapRequest(xml), charset));
        return request;
    }

    private String buildSoapRequest(String CDATA) {
        return String.format("""
        <?xml version="1.0" encoding="%s"?>
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:axi="%s">
           <soapenv:Header/>
           <soapenv:Body>
              <axi:%s>
                 <axi:%s><![CDATA[%s]]></axi:%s>
              </axi:%s>
           </soapenv:Body>
        </soapenv:Envelope>
        """, charset, SOAP_REQUEST_URI, soapRequestLocalName, elementName, CDATA, elementName, soapRequestLocalName);
    }

    public String extractBodyOfSoapResponse(String soapResponse) {
        JSONObject bodyOfResponseByObj = XML.toJSONObject(soapResponse);
        String bodyOfResponse;
        try {
            bodyOfResponse = bodyOfResponseByObj.getJSONObject("SOAP-ENV:Envelope")
                    .getJSONObject("SOAP-ENV:Body")
                    .getJSONObject(String.format("ns2:%s", soapResponseLocalName))
                    .getString(String.format("ns2:%s", responseElementName));
        } catch (Exception e) {
            throw new RuntimeException("Unexpected structure of SOAP response", e);
        }

        return bodyOfResponse;
    }

    public String extractBodyOfSoapResponse(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return null;
        }

        try {
            String soapResponse = EntityUtils.toString(entity, charset);
            return extractBodyOfSoapResponse(soapResponse);
        } catch (Exception e) {
            throw new RuntimeException("a");
        }
    }
}
