package axiomatika.converter.jsonconverter.exception;

public enum ClientExceptionName {
    VALIDATION_EXCEPTION(
            800
    ),
    GROUP_VALIDATION_EXCEPTION(
            801
    ),
    INCORRECT_JSON(
            802
    ),
    INCORRECT_CONVERT_REQUEST(
            803
    );

    private final int apiErrorCode;

    ClientExceptionName(int apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public int getApiErrorCode() {
        return apiErrorCode;
    }
}
