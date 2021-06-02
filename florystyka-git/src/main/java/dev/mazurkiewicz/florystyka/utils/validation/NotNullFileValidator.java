package dev.mazurkiewicz.florystyka.utils.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullFileValidator implements ConstraintValidator<NotNullFile, MultipartFile> {
    public void initialize(NotNullFile constraint) {
    }

    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null;
    }
}
