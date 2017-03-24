package inforkids.vis.style;

import inforkids.core.graph.Field;
import inforkids.utils.io.ImageLoader;
import inforkids.vis.style.FieldStyleSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicFieldStyleSheet implements FieldStyleSheet {

    private final Image GROUND;
    private final Image WALL;
    private final Image GOAL;


    public BasicFieldStyleSheet() {

        /* basic */
        GROUND = ImageLoader.loadImage(getClass(), "/labyrinth/ground_yellow_bordered.png");
        WALL = ImageLoader.loadImage(getClass(), "/labyrinth/wall_blue.png");


        /* field with flag */
        int gridwidth = 320;
        int gridheight = 320;
        GOAL = new BufferedImage(gridwidth, gridheight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = GOAL.getGraphics();
        g.drawImage(GROUND, 0, 0, null);
        int w = 270;
        int h = 300;
        g.drawImage(
                ImageLoader.loadImage(getClass(), "/labyrinth/flag.png"),
                (gridwidth - w) / 2,
                (gridheight - h) / 2,
                w,
                h,
                null);
    }


    @Override
    public Image get(Field field) {
        switch (field.getType()) {
            case GROUND:
                return field.isGoalField() ? GOAL : GROUND;
            case WALL:
                return WALL;
        }
        return null;
    }

    @Override
    public Image getGround() {
        return GROUND;
    }

    @Override
    public Image getWall() {
        return WALL;
    }

    @Override
    public Image getGoal() {
        return GOAL;
    }
}