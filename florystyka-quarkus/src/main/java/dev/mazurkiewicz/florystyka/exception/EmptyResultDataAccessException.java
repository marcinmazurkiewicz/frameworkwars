package dev.mazurkiewicz.florystyka.exception;

import javax.ws.rs.core.Response;

public class EmptyResultDataAccessException extends ResponseMappedException {

    public EmptyResultDataAccessException(String msg, int expectedSize) {
        super(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), String.format("%s: expected %d", expectedSize));
    }
}
