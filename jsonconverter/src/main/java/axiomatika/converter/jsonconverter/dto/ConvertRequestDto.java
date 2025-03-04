package axiomatika.converter.jsonconverter.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

public class ConvertRequestDto {
    private String data;

    public ConvertRequestDto() {
        ;
    }

    public ConvertRequestDto(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

