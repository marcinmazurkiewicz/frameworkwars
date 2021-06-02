package dev.mazurkiewicz.florystyka.config;

import dev.mazurkiewicz.florystyka.utils.validation.MultipartPropertyEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @InitBinder
    private void multipartFileBinder(WebDataBinder binder) {
        binder.setBindEmptyMultipartFiles(true);
        binder.registerCustomEditor(MultipartFile.class, new MultipartPropertyEditor());
    }
}
