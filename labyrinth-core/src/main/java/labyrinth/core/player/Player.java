package labyrinth.core.player;

import labyrinth.core.graph.Field;
import labyrinth.core.move.MultiMove;
import labyrinth.core.move.SingleMove;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Player {

    Field getField();

    /**
     * @return If walk was successful. Not successful means e.g. field in direction is a wall
     */
    boolean walkRandomSingleMove();

    /**
     * @return If walk was successful. Not successful means e.g. field in direction is a wall
     */
    boolean walk(SingleMove move);

    void walk(MultiMove move);
}
