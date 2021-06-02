package dev.mazurkiewicz.florystyka.exception;

import dev.mazurkiewicz.florystyka.exception.validation.ErrorType;

public class ResponseMappedException extends RuntimeException {
    private final int status;
    private final ErrorType errorType;

    public ResponseMappedException(int status, ErrorType errorType) {
        super();
        this.status = status;
        this.errorType = errorType;
    }

    public ResponseMappedException(int status, String message) {
        super(message);
        this.status = status;
        this.errorType = ErrorType.UNKNOWN;
    }

    public ResponseMappedException(int status, ErrorType errorType, String message) {
        super(message);
        this.status = status;
        this.errorType = errorType;
    }

    public int getStatus() {
        return status;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
