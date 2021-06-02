package dev.mazurkiewicz.florystyka.resource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService service;

    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @GetMapping(value = "/img/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("filename") String filename) {
        ByteArrayResource resource = new ByteArrayResource(service.getImage(filename));
        HttpHeaders headers = new HttpHeaders();
        String[] nameSplit = filename.split("\\.");
        String extension = nameSplit[nameSplit.length - 1];
        if ("png".equals(extension)) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if ("jpg".equals(extension) ||
                "jpeg".equals(extension)) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        headers.setContentLength(resource.contentLength());
        return new ResponseEntity<>(resource,
                headers,
                HttpStatus.OK);
    }
}
