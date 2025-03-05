package axiomatika.converter.jsonconverter.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseClientException {
    public ValidationException(String reason, ClientExceptionName exceptionName) {
        super(reason, exceptionName, HttpStatus.BAD_REQUEST);
    }
}
