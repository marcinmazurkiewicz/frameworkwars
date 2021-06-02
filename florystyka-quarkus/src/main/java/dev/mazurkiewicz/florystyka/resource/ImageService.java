package dev.mazurkiewicz.florystyka.resource;

import dev.mazurkiewicz.florystyka.exception.ResourceNotFoundException;
import dev.mazurkiewicz.florystyka.exception.ResponseMappedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;

@ApplicationScoped
public class ImageService {

    private final String resourcesFolder;
    private final String questionsImgFolder;

    public ImageService(@ConfigProperty(name = "dev.mazurkiewicz.florystyka.resourcesFolder") String resourcesFolder,
                        @ConfigProperty(name = "dev.mazurkiewicz.florystyka.questionsImgFolder") String questionsImgFolder) {
        this.resourcesFolder = resourcesFolder;
        this.questionsImgFolder = questionsImgFolder;
    }

    public byte[] getImage(String filename) {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(getImageFolderPath());
        fullPath.append(filename);

        try {
            InputStream input = new FileInputStream(fullPath.toString());
            return input.readAllBytes();
        } catch (FileNotFoundException e) {
            String msg = (e.getMessage().contains("Permission denied")) ?
                    String.format("Cannot open image %s", filename) :
                    String.format("Image %s doesn't exist", filename);
            throw new ResourceNotFoundException(msg);
        } catch (IOException e) {
            throw new ResponseMappedException(507, String.format("Cannot access file %s: %s", filename, e.getMessage()));
        }
    }

    private String getImageFolderPath() {
        StringBuilder fullPath = new StringBuilder();
        fullPath.append(resourcesFolder);
        if (!resourcesFolder.endsWith(File.separator) && !questionsImgFolder.startsWith(File.separator))
            fullPath.append(File.separator);
        fullPath.append(questionsImgFolder);
        fullPath.append(File.separator);
        return fullPath.toString();
    }
}
