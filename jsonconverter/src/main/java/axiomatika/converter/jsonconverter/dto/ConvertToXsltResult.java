package axiomatika.converter.jsonconverter.dto;

import java.util.Objects;

public class ConvertToXsltResult {
    private Long jsonId;
    private Long xsltId;
    private String result;

    public ConvertToXsltResult() {
        ;
    }

    public ConvertToXsltResult(Long jsonId, Long xsltId, String result) {
        this.jsonId = jsonId;
        this.xsltId = xsltId;
        this.result = result;
    }

    public Long getJsonId() {
        return jsonId;
    }

    public void setJsonId(Long jsonId) {
        this.jsonId = jsonId;
    }

    public Long getXsltId() {
        return xsltId;
    }

    public void setXsltId(Long xsltId) {
        this.xsltId = xsltId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertToXsltResult that = (ConvertToXsltResult) o;
        return Objects.equals(jsonId, that.jsonId)
                && Objects.equals(xsltId, that.xsltId)
                && Objects.equals(result, that.result);
    }
}
