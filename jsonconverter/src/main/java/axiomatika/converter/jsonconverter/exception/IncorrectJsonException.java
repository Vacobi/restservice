package axiomatika.converter.jsonconverter.exception;

import org.springframework.http.HttpStatus;

public class IncorrectJsonException extends BaseClientException {
    private final String incorrectJson;

    public IncorrectJsonException(String incorrectJson) {
        super(
                String.format("Json\n%s\nIs incorrect", incorrectJson),
                ClientExceptionName.INCORRECT_JSON,
                HttpStatus.BAD_REQUEST
        );
        this.incorrectJson = incorrectJson;
    }
}
