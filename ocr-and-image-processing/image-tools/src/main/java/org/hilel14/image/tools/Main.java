package org.hilel14.image.tools;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
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
        // ImageMagic command: convert -auto-orient -resize 550x380
        try {
            //imagePlusDemo();
            //pdfBoxDemo();
            //commonsImaging1();
            Path source = Paths.get("data/small.landscape.in.jpeg");
            Path target = Paths.get("data/small.landscape.out.png");
            ThumbnailCreator2.createThumbnail(source, target, 550, 380);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void imagePlusDemo() {
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
        ImageIOUtil.writeImage(bim, "src/test/out/1.pdf-box.render.jpg", 96);
    }

    static void commonsImaging1() throws Exception {
        final File sourceFile = new File("src/test/resources/samples/2.jpg");
        final Dimension dimension = Imaging.getImageSize(sourceFile);
        final BufferedImage sourceImage = Imaging.getBufferedImage(sourceFile);
        int scaleX = (int) (sourceImage.getWidth() / 550.0);
        int scaleY = (int) (sourceImage.getHeight() / 380.0);
        Image scaledImage = sourceImage.getScaledInstance(550, -1, Image.SCALE_SMOOTH);
        BufferedImage targetImage = new BufferedImage(550, scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        targetImage.createGraphics().drawImage(scaledImage, 0, 0, null);
        final ImageFormat format = ImageFormats.PNG;
        final Map<String, Object> optionalParams = new HashMap<>();
        final File targetFile = new File("src/test/out/2.commons-1.png");
        Imaging.writeImage(targetImage, targetFile, format, optionalParams);
    }

    static void commonsImaging2() throws Exception {
        final File sourceFile = new File("src/test/resources/samples/2.jpg");
        final Dimension dimension = Imaging.getImageSize(sourceFile);
        double ratio = 550.0 / dimension.getWidth();
        final BufferedImage sourceImage = Imaging.getBufferedImage(sourceFile);
        BufferedImage targetImage = scaleBufferedImage(sourceImage, ratio);
        final ImageFormat format = ImageFormats.PNG;
        final Map<String, Object> optionalParams = new HashMap<>();
        final File targetFile = new File("src/test/out/2.commons.png");
        Imaging.writeImage(targetImage, targetFile, format, optionalParams);
    }

    static private BufferedImage scaleBufferedImage(BufferedImage source, double ratio) {
        int w = (int) (source.getWidth() * ratio);
        int h = (int) (source.getHeight() * ratio);
        BufferedImage bi = getCompatibleImage(w, h);
        Graphics2D g2d = bi.createGraphics();
        double xScale = (double) w / source.getWidth();
        double yScale = (double) h / source.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
        g2d.drawRenderedImage(source, at);
        g2d.dispose();
        return bi;
    }

    static private BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        return image;
    }

}
