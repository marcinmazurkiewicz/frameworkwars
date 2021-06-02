package dev.mazurkiewicz.florystyka.utils.validation;

import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyEditorSupport;

public class MultipartPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        MultipartFile file = (MultipartFile) getValue();

        return file == null ? "" : file.getOriginalFilename();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        return;
    }
}
