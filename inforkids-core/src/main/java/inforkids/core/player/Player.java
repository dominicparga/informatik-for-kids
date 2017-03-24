package inforkids.core.player;

import inforkids.core.graph.Field;
import inforkids.core.move.MultiMove;
import inforkids.core.move.SingleMove;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Player {

    Field getField();

    void setField(Field field);

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
