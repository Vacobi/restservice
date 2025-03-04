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

    @Bean
    public String soapRequestUri() {
        return requestUri;
    }

    @Bean
    public String charset() {
        return charset;
    }
}
