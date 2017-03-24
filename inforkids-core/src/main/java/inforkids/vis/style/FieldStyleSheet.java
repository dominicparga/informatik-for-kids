package inforkids.vis.style;

import inforkids.core.graph.Field;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface FieldStyleSheet {

    Image get(Field field);

    Image getGround();

    Image getWall();

    Image getGoal();
}
