package dev.mazurkiewicz.florystyka.pdf;

import com.lowagie.text.Image;
import dev.mazurkiewicz.florystyka.exception.PdfRenderException;
import dev.mazurkiewicz.florystyka.resource.ImageService;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

class ImageReplacedElementFactory implements ReplacedElementFactory {

    private final ReplacedElementFactory superFactory;
    private final ImageService imageService;

    ImageReplacedElementFactory(ReplacedElementFactory superFactory, ImageService imageService) {
        this.superFactory = superFactory;
        this.imageService = imageService;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
                                                 UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String nodeName = element.getNodeName();
        if ("img".equals(nodeName)) {
            if (!element.hasAttribute("src")) {
                throw new PdfRenderException("Image has missing a `src` attribute.");
            }
            try {
                final byte[] bytes = imageService.getImage(element.getAttribute("src"));
                final Image image = Image.getInstance(bytes);
                final FSImage fsImage = new ITextFSImage(image);
                if ((cssWidth != -1) || (cssHeight != -1)) {
                    fsImage.scale(cssWidth, cssHeight);
                }
                return new ITextImageElement(fsImage);

            } catch (Exception e) {
                throw new PdfRenderException(String.format("There was a problem with the handling of the image: %s", e.getMessage()));
            }
        }
        return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        this.superFactory.reset();
    }

    @Override
    public void remove(Element e) {
        this.superFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        this.superFactory.setFormSubmissionListener(listener);
    }
}