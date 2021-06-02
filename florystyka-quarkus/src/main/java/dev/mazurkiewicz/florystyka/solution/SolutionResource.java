package dev.mazurkiewicz.florystyka.solution;

import dev.mazurkiewicz.florystyka.exception.IncorrectResultSizeDataAccessException;
import dev.mazurkiewicz.florystyka.exception.ResponseMappedException;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/v3/solutions")
public class SolutionResource {

    private final SolutionService service;

    public SolutionResource(SolutionService service) {
        this.service = service;
    }

    @POST
    @Path("/single")
    public SolutionResponse checkAnswer(@Valid SolutionRequest solution) {
        return service.checkSingleAnswer(solution);
    }

    @POST
    @Path("/test")
    public SolutionResponse checkTest(@Valid List<SolutionRequest> solutions) {
        try {
            return service.checkTest(solutions);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new ResponseMappedException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

}

