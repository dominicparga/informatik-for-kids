package labyrinth.utils.io;

import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * @author Dominic Parga Cacheiro
 */
public class ImageLoader {

    private static final Logger logger = new EasyMarkableLogger(ImageLoader.class);

    public static Image loadImage(Class<?> clazz, String name) {
        try {
            return ImageIO.read(clazz.getResource(name));
        } catch (Exception e) {
            logger.info("Image not found. Trying to load the replacement-image instead.");
            try {
                return ImageIO.read(clazz.getResource("/undefined.png"));
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }
}
