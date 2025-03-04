package axiomatika.converter.jsonconverter.dto;

public class ConvertResponseDto {
    private String data;

    public ConvertResponseDto() {
        ;
    }

    public ConvertResponseDto(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
