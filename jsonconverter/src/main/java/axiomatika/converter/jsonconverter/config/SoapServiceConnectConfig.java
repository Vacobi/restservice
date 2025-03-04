package axiomatika.converter.jsonconverter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapServiceConnectConfig {

    @Value("${spring.soap-service-connection.soap_request_uri}")
    private String requestUri;

    @Value("${spring.soap-service-connection.charset}")
    private String charset;

    @Value("${spring.soap-service-connection.soap-request-local-name}")
    private String soapRequestLocalName;

    @Value("${spring.soap-service-connection.element-name}")
    private String elementName;

    @Value("${spring.soap-service-connection.soap-response-local-name}")
    private String soapResponseLocalName;

    @Value("${spring.soap-service-connection.response-element-name}")
    private String responseElementName;

    @Bean
    public String soapRequestUri() {
        return requestUri;
    }

    @Bean
    public String charset() {
        return charset;
    }

    @Bean
    public String soapRequestLocalName() {
        return soapRequestLocalName;
    }

    @Bean
    public String elementName() {
        return elementName;
    }

    @Bean
    public String soapResponseLocalName() {
        return soapResponseLocalName;
    }

    @Bean
    public String responseElementName() {
        return responseElementName;
    }
}
