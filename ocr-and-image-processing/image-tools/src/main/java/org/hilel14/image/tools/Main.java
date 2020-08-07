package org.hilel14.image.tools;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

/**
 *
 * @author hilel
 */
public class Main {

    public static void main(String[] args) {
        try {
            imagePlusDemo();
            pdfBoxDemo();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void imagePlusDemo() {
        // convert.image.preview=/usr/bin/convert -auto-orient -resize 550x380
        ImagePlus imagePlus = IJ.openImage("src/test/resources/samples/2.jpg");
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        //imageProcessor.scale(0.5, 0.5);
        int w = imageProcessor.getWidth();
        int h = imageProcessor.getHeight();
        imageProcessor = imageProcessor.resize(550);
        imagePlus.setProcessor(imageProcessor);
        IJ.saveAs(imagePlus, "jpg", "src/test/out/2.image-plus.resize.jpg");
    }

    static void pdfBoxDemo() throws IOException {
        //convert.pdf.preview=/usr/bin/convert -crop 550x380+12+12
        PDDocument document = PDDocument.load(new File("src/test/resources/samples/1.pdf"));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 96, ImageType.RGB);        
        ImageIOUtil.writeImage(bim,  "src/test/out/1.pdf-box.render.jpg", 96);
    }

}
