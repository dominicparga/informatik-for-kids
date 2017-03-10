package labyrinth.utils.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * @author Dominic Parga Cacheiro
 */
public class ImageLoader {

    public static Image loadImage(Class<?> clazz, String name) {
        try {
            return ImageIO.read(clazz.getResource(name));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
