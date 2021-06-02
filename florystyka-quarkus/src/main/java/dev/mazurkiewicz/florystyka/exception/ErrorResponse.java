package dev.mazurkiewicz.florystyka.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mazurkiewicz.florystyka.exception.validation.ErrorInfo;
import dev.mazurkiewicz.florystyka.exception.validation.ErrorType;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String path;
    private final ErrorType error;
    private final Map<String, List<ErrorInfo>> fieldsErrorInfo;

    public ErrorResponse(int status, String message, String path, ErrorType error) {
        this(status, message, path, error, null);
    }

    public ErrorResponse(int status, String message, String path, ErrorType error, Map<String, List<ErrorInfo>> fieldsErrorInfo) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.error = error;
        this.fieldsErrorInfo = fieldsErrorInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public ErrorType getError() {
        return error;
    }

    public Map<String, List<ErrorInfo>> getFieldsErrorInfo() {
        return fieldsErrorInfo;
    }
}



