package labyrinth.utils.io;

import microtrafficsim.utils.logging.EasyMarkableLogger;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Dominic Parga Cacheiro
 */
public class ImageLoader {

    private static final Logger logger = new EasyMarkableLogger(ImageLoader.class);

    public static BufferedImage loadImage(Class<?> clazz, String filename) {
        try {
            return ImageIO.read(clazz.getResource(filename));
        } catch (Exception e) {
            logger.info("Image \"" + filename + "\" not found. Trying to load the replacement-image instead.");
            try {
                return ImageIO.read(clazz.getResource("/undefined.png"));
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    /**
     * @param filename image file name
     * @param w sprite width
     * @param h sprite height
     * @return
     */
    public static BufferedImage[][] loadSprite(String filename, int w, int h) {

        /* load whole image */
        BufferedImage spritesheet = loadImage(ImageLoader.class, filename);
        if (spritesheet == null)
            return null;


        /* get sprites out of whole image */
        int rows = spritesheet.getHeight() / h;
        int columns = spritesheet.getWidth() / w;
        BufferedImage[][] sprites = new BufferedImage[rows][columns];

        for(int row = 0; row < rows; row++)
            for(int column = 0; column < columns; column++)
                sprites[row][column] = spritesheet.getSubimage(column * w, row * h, w, h);

        return sprites;
    }
}
