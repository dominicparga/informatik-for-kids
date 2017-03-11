package inforkids.vis.style;

import inforkids.core.graph.Field;
import inforkids.core.player.Player;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface GUIStyleSheet {

    Image get(Player player);

    Image get(Field field);

    Color getBackgroundColor();
}
