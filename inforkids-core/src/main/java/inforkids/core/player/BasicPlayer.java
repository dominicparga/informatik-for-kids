package inforkids.core.player;

import inforkids.core.graph.Direction;
import inforkids.core.graph.Field;
import inforkids.core.move.MultiMove;
import inforkids.core.move.SingleMove;
import inforkids.entities.PlayerEntity;
import microtrafficsim.math.random.distributions.impl.Random;

import java.util.ArrayList;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicPlayer implements Player {

    private PlayerEntity entity;
    private Field field;
    private final Random random;
    /* dynamic moving */
    private SingleMove currentMove;


    public BasicPlayer(long seed, Field startField) {
        this();
        random.setSeed(seed);
        field = startField;
    }

    public BasicPlayer(Field startField) {
        this();
        field = startField;
    }

    public BasicPlayer(long seed) {
        this();
        random.setSeed(seed);
    }

    public BasicPlayer() {
        random = new Random();
    }


    /*
    |============|
    | (i) Player |
    |============|
    */
    @Override
    public PlayerEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(PlayerEntity entity) {
        this.entity = entity;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public void setField(Field field) {
        walkTo(field);
    }

    @Override
    public boolean isWalking() {
        if (currentMove == null)
            return false;

        Field destination = field.get(getDirection(currentMove));
        return destination != null;
    }

    @Override
    public SingleMove getCurrentMove() {
        return currentMove;
    }

    @Override
    public void startWalking(SingleMove move) {
        currentMove = move;
    }

    @Override
    public boolean walkRandomSingleMove() {
        ArrayList<Field> neighbours = field.getNeighbours();
        int randomIdx               = random.nextInt(neighbours.size());
        Field randomField           = neighbours.get(randomIdx);
        return walkTo(randomField);
    }

    @Override
    public boolean walk(SingleMove move) {
        return walkTo(field.get(getDirection(move)));
    }

    @Override
    public void walk(MultiMove move) {
        move.forEach(this::walk);
    }


    /*
    |=======|
    | utils |
    |=======|
    */
    private boolean walkTo(Field newField) {
        currentMove = null;

        if (newField != null) {
            field = newField;
            return true;
        }
        return false;
    }

    private Direction getDirection(SingleMove move) {

        if (move == SingleMove.LEFT)
            return Direction.LEFT;

        if (move == SingleMove.DOWN)
            return Direction.DOWN;

        if (move == SingleMove.RIGHT)
            return Direction.RIGHT;

        if (move == SingleMove.UP)
            return Direction.UP;

        throw new UnsupportedOperationException("SingleMove is not registered.");
    }
}
