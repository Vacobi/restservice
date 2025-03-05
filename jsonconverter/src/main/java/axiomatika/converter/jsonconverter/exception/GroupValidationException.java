package axiomatika.converter.jsonconverter.exception;

import java.util.List;

public class GroupValidationException extends ValidationException {
    private final List<? extends ValidationException> exceptions;

    public GroupValidationException(List<ValidationException> exceptions) {
        super(
                "Group validation exception.",
                ClientExceptionName.GROUP_VALIDATION_EXCEPTION
        );
        this.exceptions = exceptions;
    }

    public List<? extends ValidationException> getExceptions() {
        return exceptions;
    }
}
