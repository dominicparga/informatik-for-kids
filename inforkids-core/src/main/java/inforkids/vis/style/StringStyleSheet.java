package inforkids.vis.style;

import inforkids.core.graph.Field;
import inforkids.core.graph.Labyrinth;
import inforkids.core.player.Player;

/**
 * @author Dominic Parga Cacheiro
 */
public interface StringStyleSheet {

    String get(Labyrinth labyrinth);

    String get(Labyrinth labyrinth, Player player);

    String get(Field field);

    String get(Player player);
}
