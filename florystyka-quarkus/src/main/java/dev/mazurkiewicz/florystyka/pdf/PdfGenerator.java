package dev.mazurkiewicz.florystyka.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import dev.mazurkiewicz.florystyka.exception.PdfRenderException;
import dev.mazurkiewicz.florystyka.question.Question;
import dev.mazurkiewicz.florystyka.resource.ImageService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class PdfGenerator {

    private final ImageService imageService;
    private final String resourcesFolder;

    public PdfGenerator(ImageService imageService,
                        @ConfigProperty(name = "dev.mazurkiewicz.florystyka.resourcesFolder") String resourcesFolder) {
        this.imageService = imageService;
        this.resourcesFolder = resourcesFolder;
    }

    protected String parseThymeleafTemplate(List<Question> questions) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("questions", questions);
        boolean isQuestionNumberEven = questions.size() % 2 == 0;
        int answerTableOffset = questions.size() / 2;
        if (!isQuestionNumberEven) {
            answerTableOffset++;
        }
        int answerTableRows = answerTableOffset;
        context.setVariable("answerOffset", answerTableOffset);
        context.setVariable("answersTableRows", answerTableRows);
        return templateEngine.process("templates/pdf_template", context);
    }

    public byte[] generatePdfFromHtml(String html)
            throws PdfRenderException {
        StringBuilder fontPath = new StringBuilder();
        fontPath.append(resourcesFolder);
        if (!resourcesFolder.endsWith(File.separator))
            fontPath.append(File.separator);
        fontPath.append("fonts/arial.ttf");

        File fontResource = new File(fontPath.toString());
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver resolver = renderer.getFontResolver();
        renderer.getSharedContext().setReplacedElementFactory(
                new ImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), imageService));
        try {
            resolver.addFont(fontResource.toURI().toURL().getPath(), BaseFont.IDENTITY_H, true);
        } catch (DocumentException | IOException e) {
            System.err.println("Failed to set font: " + e.getMessage());
        }
        renderer.setDocumentFromString(html);
        renderer.layout();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            renderer.createPDF(out);
            return out.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new PdfRenderException(String.format("PDF file rendering failed: %s", e.getMessage()));
        }
    }

    public byte[] generateTest(Set<Question> questions) throws PdfRenderException {
        ArrayList<Question> questionArrayList = new ArrayList<>(questions);
        String html = parseThymeleafTemplate(questionArrayList);
        return generatePdfFromHtml(html);
    }
}
