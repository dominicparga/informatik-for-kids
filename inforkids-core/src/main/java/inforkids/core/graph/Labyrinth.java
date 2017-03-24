package inforkids.core.graph;

import inforkids.core.player.Player;
import inforkids.utils.Matrix2;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Labyrinth extends Matrix2<Field> {

    Player getPlayer();

    Field getStartField();
}
