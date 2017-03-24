package inforkids.vis.style;

import inforkids.core.graph.Field;
import inforkids.core.player.Player;

import java.awt.*;

/**
 * @author Dominic Parga Cacheiro
 */
public interface LabyrinthStyleSheet {

    PlayerStyleSheet getPlayerStyle();

    FieldStyleSheet getFieldStyle();

    Color getBackgroundColor();
}
