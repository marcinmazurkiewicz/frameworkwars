package dev.mazurkiewicz.florystyka.exception.validation;

import java.util.Arrays;
import java.util.List;

public enum ErrorType {
    ABOVE_MAX(List.of("DecimalMax", "Max")),
    EMPTY(List.of("NotEmpty", "NotNull", "NotBlank", "NotNullFile")),
    AT_LEAST_ONE(List.of("AtLeastOne")),
    NOT_MAIL(List.of("Email")),
    NOT_MATCH(List.of("FieldMatch")),
    NOT_UNIQUE(List.of("UniqueMail")),
    UNDER_MIN(List.of("DecimalMin", "Min")),
    TYPE_MISMATCH(List.of("FileFormat", "FileTypeException")),
    DOCUMENT_CONTENT_NOT_VALID(List.of("IllegalImportDocumentContent")),
    FORBIDDEN(List.of("ForbiddenAccessException")),
    CREDENTIALS_ERROR(List.of("BadCredentialsException")),
    TOKEN_EXPIRED(List.of("TokenExpiredException", "ExpiredJwtException")),
    UNAUTHORIZED(List.of("UnauthorizedAccessException")),
    VALIDATION_ERROR(List.of("BindException", "MethodArgumentNotValidException")),
    PARSE_ERROR(List.of("HttpMessageNotReadableException")),
    FILE_PROCESSING_ERROR(List.of("IOException")),
    UNKNOWN(List.of());

    private final List<String> codeNames;

    ErrorType(List<String> codeNames) {
        this.codeNames = codeNames;
    }

    public static ErrorType valueOfCode(String codeName) {

        return Arrays.stream(ErrorType.values())
                .filter(errorType -> errorType.codeNames.contains(codeName))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
