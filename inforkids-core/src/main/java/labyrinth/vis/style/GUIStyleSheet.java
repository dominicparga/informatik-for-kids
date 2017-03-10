package labyrinth.vis.style;

import labyrinth.core.graph.Field;
import labyrinth.core.player.Player;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface GUIStyleSheet {

    Image get(Player player);

    Image get(Field field);

    Color getBackgroundColor();
}
