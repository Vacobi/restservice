package axiomatika.converter.jsonconverter.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://localhost:8081/ws/server", name = "getXsltRequest")
public class XsltRequestDto {
    private String xmlData;

    public XsltRequestDto() {
        ;
    }

    public XsltRequestDto(String xmlData) {
        this.xmlData = xmlData;
    }

    @XmlElement(namespace = "http://localhost:8081/ws/server", name = "xmlData")
    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }
}

