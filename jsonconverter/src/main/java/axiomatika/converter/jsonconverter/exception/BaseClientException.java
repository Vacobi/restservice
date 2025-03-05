package axiomatika.converter.jsonconverter.exception;

import org.springframework.http.HttpStatusCode;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseClientException extends RuntimeException {

    private ClientExceptionName exceptionName;

    private HttpStatusCode statusCode;

    public BaseClientException(
            String reason,
            ClientExceptionName exceptionName,
            HttpStatusCode HttpStatusCode
    ) {
        super(reason);
        this.exceptionName = exceptionName;
        this.statusCode = HttpStatusCode;
    }

    public BaseClientException(
            String reason,
            ClientExceptionName exceptionName
    ) {
        super(reason);
        this.exceptionName = exceptionName;
    }

    public Map<String, Object> properties() {
        return new LinkedHashMap<>();
    }
}
