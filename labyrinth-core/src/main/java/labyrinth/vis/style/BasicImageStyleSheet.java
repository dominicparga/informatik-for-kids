package labyrinth.vis.style;

import labyrinth.core.graph.Field;
import labyrinth.core.player.Player;
import labyrinth.utils.io.ImageLoader;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicImageStyleSheet implements ImageStyleSheet {

    private final Image FIELD_GROUND;
    private final Image FIELD_WALL;
    private final Image PLAYER;

    public BasicImageStyleSheet() {
        FIELD_GROUND = ImageLoader.loadImage(getClass(), "/ground.png");
        FIELD_WALL = ImageLoader.loadImage(getClass(), "/wall.png");
        PLAYER = ImageLoader.loadImage(getClass(), "/player.png");
    }

    @Override
    public Image get(Player player) {
        return PLAYER;
    }

    @Override
    public Image get(Field field) {
        switch (field.getType()) {
            case GROUND:
                return FIELD_GROUND;
            case WALL:
                return FIELD_WALL;
        }
        return null;
    }
}
