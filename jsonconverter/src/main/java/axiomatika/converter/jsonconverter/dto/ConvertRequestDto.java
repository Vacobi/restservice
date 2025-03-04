package axiomatika.converter.jsonconverter.dto;

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

