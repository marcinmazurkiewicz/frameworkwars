package dev.mazurkiewicz.florystyka.exception;

import javax.ws.rs.core.Response;

public class IncorrectResultSizeDataAccessException extends ResponseMappedException {

    public IncorrectResultSizeDataAccessException(String msg, int expectedSize, int resultSize) {
        super(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), String.format("%s: expected %d but received %d", expectedSize, resultSize));
    }
}
