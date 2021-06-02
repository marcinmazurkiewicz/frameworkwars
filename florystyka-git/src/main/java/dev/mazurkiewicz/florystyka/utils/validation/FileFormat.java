package dev.mazurkiewicz.florystyka.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileFormatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileFormat {
    String message() default "{dev.mazurkiewicz.florystyka.validation.FileFormat.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] contentTypes() default {};
}
