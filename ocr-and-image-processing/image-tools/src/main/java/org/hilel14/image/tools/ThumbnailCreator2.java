package org.hilel14.image.tools;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hilel
 */
public class ThumbnailCreator2 {

    static final Logger LOGGER = Logger.getLogger(ThumbnailCreator2.class.getName());

    static void createThumbnail(Path source, Path target, int maxWidth, int maxHeight)
            throws Exception {
        LOGGER.log(Level.INFO, "source file = {0}", source);
        BufferedImage sourceImage = ImageIO.read(source.toFile());
        Dimension scaledDimension = ThumbnailCreator.calculateDimension(sourceImage.getWidth(), sourceImage.getHeight(), maxWidth, maxHeight);
        LOGGER.log(Level.INFO, "scaledDimension = {0}", scaledDimension);
        Image scaledImage = sourceImage.getScaledInstance(scaledDimension.width, scaledDimension.height, Image.SCALE_SMOOTH);
        BufferedImage targetImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        targetImage.createGraphics().drawImage(scaledImage, 0, 0, null);
        ImageIO.write(targetImage, "png", target.toFile());
    }

}
