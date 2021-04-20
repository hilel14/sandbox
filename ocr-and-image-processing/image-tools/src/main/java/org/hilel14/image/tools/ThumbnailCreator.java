/**
 *
 * ImageMagic command: convert input-file -auto-orient -resize 550x380 output-file
 * Only sequential, baseline JPEGs are supported at the moment
 */
package org.hilel14.image.tools;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;

/**
 *
 * @author hilel
 */
public class ThumbnailCreator {

    static final Logger LOGGER = Logger.getLogger(ThumbnailCreator.class.getName());

    static void createThumbnail(Path source, Path target, int maxWidth, int maxHeight)
            throws Exception {
        LOGGER.log(Level.INFO, "source file = {0}", source);
        Dimension origDimension = Imaging.getImageSize(source.toFile());
        LOGGER.log(Level.INFO, "origDimension = {0}", origDimension);
        Dimension scaledDimension = calculateDimension(origDimension.width, origDimension.height, maxWidth, maxHeight);
        LOGGER.log(Level.INFO, "scaledDimension = {0}", scaledDimension);
        BufferedImage sourceImage = Imaging.getBufferedImage(source.toFile());
        Image scaledImage = sourceImage.getScaledInstance(scaledDimension.width, scaledDimension.height, Image.SCALE_SMOOTH);
        BufferedImage targetImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        targetImage.createGraphics().drawImage(scaledImage, 0, 0, null);
        Map<String, Object> optionalParams = new HashMap<>();
        Imaging.writeImage(targetImage, target.toFile(), ImageFormats.PNG, optionalParams);
    }

    static Dimension calculateDimension(int sourceWidth, int sourceHeight, int maxWidth, int maxHeight) {
        Dimension result = new Dimension(-1, -1);
        if (sourceWidth <= maxWidth && sourceHeight <= maxHeight) {
            LOGGER.log(Level.INFO, "{0} <= {1} AND {2} <= {3}", new Object[]{sourceWidth, maxWidth, sourceHeight, maxHeight});
            return result;
        }
        if (sourceWidth > maxWidth && sourceHeight <= maxHeight) {
            LOGGER.log(Level.INFO, "{0} > {1} AND {2} <= {3}", new Object[]{sourceWidth, maxWidth, sourceHeight, maxHeight});
            result.width = maxWidth;
            return result;
        }
        if (sourceHeight > maxHeight && sourceWidth <= maxWidth) {
            LOGGER.log(Level.INFO, "{2} > {3} AND {0} <= {1}", new Object[]{sourceWidth, maxWidth, sourceHeight, maxHeight});
            result.height = maxHeight;
            return result;
        }
        int widthDiff = sourceWidth - maxWidth;
        int heightDiff = sourceHeight - maxHeight;
        if (widthDiff > heightDiff) {
            LOGGER.log(Level.INFO, "{0} > {1}", new Object[]{widthDiff, heightDiff});
            result.width = maxWidth;
            return result;
        } else {
            LOGGER.log(Level.INFO, "{0} < {1}", new Object[]{widthDiff, heightDiff});
            result.height = maxHeight;
            return result;
        }
    }

}
