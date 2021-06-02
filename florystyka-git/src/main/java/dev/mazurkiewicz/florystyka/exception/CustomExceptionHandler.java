package dev.mazurkiewicz.florystyka.exception;

import dev.mazurkiewicz.florystyka.exception.validation.ErrorInfo;
import dev.mazurkiewicz.florystyka.exception.validation.ErrorType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return mapToFieldErrorResponse(ex, headers, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return mapToFieldErrorResponse(ex, headers, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = mapToErrorResponse(ex, status, request);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<Object> mapToFieldErrorResponse(BindException ex, HttpHeaders headers, WebRequest request) {
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();

        Map<String, ErrorInfo> errors = prepareFieldsErrorMap(ex);

        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Some fields are invalid", requestPath,
                        ErrorType.VALIDATION_ERROR, errors);
        return ResponseEntity.badRequest().headers(headers).body(errorResponse);
    }

    private ErrorResponse mapToErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        String requestPath = ((ServletWebRequest) request).getRequest().getRequestURI();

        return new ErrorResponse(status.value(), ex.getMessage()
                , requestPath, ErrorType.valueOfCode(ex.getClass().getSimpleName()));
    }

    private Map<String, ErrorInfo> prepareFieldsErrorMap(BindException ex) {
        Map<String, ErrorInfo> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(
                    error.getField(),
                    new ErrorInfo(ErrorType.valueOfCode(error.getCode()), error.getDefaultMessage())
            );
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(
                    error.getObjectName(),
                    new ErrorInfo(ErrorType.valueOfCode(error.getCode()), error.getDefaultMessage())
            );
        }
        return errors;
    }
}
