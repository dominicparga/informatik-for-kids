package inforkids.core.player;

import inforkids.core.graph.Field;
import inforkids.core.move.MultiMove;
import inforkids.core.move.SingleMove;
import inforkids.entities.PlayerEntity;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Player {

    PlayerEntity getEntity();

    void setEntity(PlayerEntity entity);


    Field getField();

    void setField(Field field);

    boolean isWalking();

    SingleMove getCurrentMove();

    void startWalking(SingleMove move);

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
