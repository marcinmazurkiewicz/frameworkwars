package dev.mazurkiewicz.florystyka.question;

import dev.mazurkiewicz.florystyka.exception.EmptyResultDataAccessException;
import dev.mazurkiewicz.florystyka.exception.IncorrectResultSizeDataAccessException;
import dev.mazurkiewicz.florystyka.exception.PdfRenderException;
import dev.mazurkiewicz.florystyka.exception.ResponseMappedException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/v3/questions")
public class QuestionResource {

    private final QuestionService service;

    public QuestionResource(QuestionService service) {
        this.service = service;
    }

    @GET
    @Path("/random")
    public QuestionResponse getRandomQuestion() {
        return service.getRandomQuestion();
    }

    @GET
    @Path("/{id}")
    public QuestionResponse getQuestionById(@PathParam("id") Integer id) {
        return service.getQuestionById(id);
    }

    @GET
    @Path("/test")
    public TestResponse getQuestionToTest() {
        return service.getQuestionsToTest();
    }

    @GET
    @Path("/info")
    public QuestionNumberResponse getQuestionNumber() {
        return service.countQuestions();
    }

    @GET
    @Path("/test/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdf() {
        String filename = "r26.pdf";
        byte[] pdfBytes;
        try {
            pdfBytes = service.getPdfTest();
        } catch (PdfRenderException e) {
            throw new ResponseMappedException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
        }

        return Response.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8\'\'" + filename)
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .entity(pdfBytes).build();
    }

}
