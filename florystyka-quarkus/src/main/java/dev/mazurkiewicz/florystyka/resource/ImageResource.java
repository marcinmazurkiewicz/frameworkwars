package dev.mazurkiewicz.florystyka.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/resources/img")
public class ImageResource {

    private final ImageService service;

    public ImageResource(ImageService service) {
        this.service = service;
    }

    @GET
    @Path(value = "/{filename}")
    @Produces({"image/png", "image/jpeg"})
    public Response getImage(@PathParam("filename") String filename) {
        byte[] resource = service.getImage(filename);
        String[] nameSplit = filename.split("\\.");
        String extension = nameSplit[nameSplit.length - 1];
        String contentType = "*/*";
        if ("png".equals(extension)) {
            contentType = "image/png";
        } else if ("jpg".equals(extension) ||
                "jpeg".equals(extension)) {
            contentType = "image/jpeg";
        }

        return Response.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8\'\'" + filename)
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .entity(resource)
                .build();
    }
}
