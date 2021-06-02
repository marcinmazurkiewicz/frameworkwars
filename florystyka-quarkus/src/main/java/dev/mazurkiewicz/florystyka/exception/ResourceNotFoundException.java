package dev.mazurkiewicz.florystyka.exception;

import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends ResponseMappedException {
    public ResourceNotFoundException(String msg) {
        super(Response.Status.NOT_FOUND.getStatusCode(), msg);
    }
}
