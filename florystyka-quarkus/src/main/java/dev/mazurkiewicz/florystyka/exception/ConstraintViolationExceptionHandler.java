package dev.mazurkiewicz.florystyka.exception;

import dev.mazurkiewicz.florystyka.exception.validation.ErrorInfo;
import dev.mazurkiewicz.florystyka.exception.validation.ErrorType;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        Map<String, List<ErrorInfo>> errors = mapViolationsToFieldErrorInfo(e.getConstraintViolations());
        ErrorResponse response = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(),
                "Some fields are invalid", uriInfo.getPath(), ErrorType.VALIDATION_ERROR, errors);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }

    private Map<String, List<ErrorInfo>> mapViolationsToFieldErrorInfo(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(ConstraintViolationDescriptor::new)
                .collect(Collectors.toMap(
                        ConstraintViolationDescriptor::getFieldName,
                        x -> Collections.singletonList(x.getErrorInfo()),
                        this::mergeErrorInfos
                        )
                );
    }

    private List<ErrorInfo> mergeErrorInfos(List<ErrorInfo> first, List<ErrorInfo> second) {
        return Stream.concat(first.stream(), second.stream()).collect(Collectors.toList());
    }
}
