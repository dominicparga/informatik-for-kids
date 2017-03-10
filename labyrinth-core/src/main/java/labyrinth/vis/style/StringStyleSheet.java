package labyrinth.vis.style;

import labyrinth.core.graph.Field;
import labyrinth.core.graph.Labyrinth;
import labyrinth.core.player.Player;

/**
 * @author Dominic Parga Cacheiro
 */
public interface StringStyleSheet {

    String get(Labyrinth labyrinth);

    String get(Labyrinth labyrinth, Player player);

    String get(Field field);

    String get(Player player);
}
