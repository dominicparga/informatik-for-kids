package labyrinth.vis.style;

import labyrinth.core.graph.Direction;
import labyrinth.core.graph.Field;
import labyrinth.core.player.Player;
import labyrinth.utils.io.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicGUIStyleSheet implements GUIStyleSheet {

    private BufferedImage[][] FIELD_GROUND;
    private Image FIELD_WALL;
    private Image PLAYER;

    public BasicGUIStyleSheet() {

        FIELD_GROUND = ImageLoader.loadSprite("/ground.png", 320, 320);
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
                return getGroundImage(
                        Field.isType(field.get(Direction.LEFT), Field.Type.GROUND),
                        Field.isType(field.get(Direction.DOWN), Field.Type.GROUND),
                        Field.isType(field.get(Direction.RIGHT), Field.Type.GROUND),
                        Field.isType(field.get(Direction.UP), Field.Type.GROUND)
                );
            case WALL:
                return FIELD_WALL;
        }
        return null;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.LIGHT_GRAY;
    }


    /*
    |=======|
    | utils |
    |=======|
    */
    /**
     * @param left   true <=> left neighbour is ground
     * @param bottom true <=> bottom neighbour is ground
     * @param right  true <=> right neighbour is ground
     * @param top    true <=> top neighbour is ground
     * @return the specified field image
     */
    private Image getGroundImage(boolean left, boolean bottom, boolean right, boolean top) {

        String tmp = "";
        tmp += left ? "L" : "x";
        tmp += bottom ? "B" : "x";
        tmp += right ? "R" : "x";
        tmp += top ? "T" : "x";

        switch (tmp) {
            case "LBRT":
                return FIELD_GROUND[0][0];
            case "LBRx":
                return FIELD_GROUND[0][1];
            case "LBxT":
                return FIELD_GROUND[0][2];
            case "LBxx":
                return FIELD_GROUND[0][3];
            case "LxRT":
                return FIELD_GROUND[1][0];
            case "LxRx":
                return FIELD_GROUND[1][1];
            case "LxxT":
                return FIELD_GROUND[1][2];
            case "Lxxx":
                return FIELD_GROUND[1][3];
            case "xBRT":
                return FIELD_GROUND[2][0];
            case "xBRx":
                return FIELD_GROUND[2][1];
            case "xBxT":
                return FIELD_GROUND[2][2];
            case "xBxx":
                return FIELD_GROUND[2][3];
            case "xxRT":
                return FIELD_GROUND[3][0];
            case "xxRx":
                return FIELD_GROUND[3][1];
            case "xxxT":
                return FIELD_GROUND[3][2];
            default: // xxxx
                return FIELD_GROUND[3][3];
        }
    }
}
