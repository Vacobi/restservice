package axiomatika.converter.jsonconverter.exception;

public enum ClientExceptionName {
    INCORRECT_JSON(
            801
    );

    private final int apiErrorCode;

    ClientExceptionName(int apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public int getApiErrorCode() {
        return apiErrorCode;
    }
}
