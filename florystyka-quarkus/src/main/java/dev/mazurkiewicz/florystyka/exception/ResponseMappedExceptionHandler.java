package dev.mazurkiewicz.florystyka.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseMappedExceptionHandler implements ExceptionMapper<ResponseMappedException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ResponseMappedException e) {
        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage(), uriInfo.getPath(), e.getErrorType());
        return Response
                .status(e.getStatus())
                .entity(response)
                .build();
    }
}
