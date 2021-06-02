package dev.mazurkiewicz.florystyka.utils.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileFormatValidator implements ConstraintValidator<FileFormat, MultipartFile> {

    private List<String> contentTypes;

    @Override
    public void initialize(FileFormat constraintAnnotation) {
        this.contentTypes = Arrays.asList(constraintAnnotation.contentTypes());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || this.contentTypes.contains(file.getContentType());
    }
}
