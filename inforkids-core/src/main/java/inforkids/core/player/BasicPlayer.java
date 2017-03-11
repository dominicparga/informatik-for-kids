package inforkids.core.player;

import inforkids.core.graph.Direction;
import inforkids.core.graph.Field;
import inforkids.core.move.MultiMove;
import inforkids.core.move.SingleMove;
import microtrafficsim.math.random.distributions.impl.Random;

import java.util.ArrayList;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicPlayer implements Player {

    private Field field;
    private Random random;


    public BasicPlayer(long seed, Field startField) {
        random = new Random(seed);
        field = startField;
    }

    public BasicPlayer(Field startField) {
        random = new Random();
        field = startField;
    }


    /*
    |============|
    | (i) Player |
    |============|
    */
    @Override
    public Field getField() {
        return field;
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

        if (move == SingleMove.LEFT)
            return walkTo(field.get(Direction.LEFT));

        if (move == SingleMove.DOWN)
            return walkTo(field.get(Direction.DOWN));

        if (move == SingleMove.RIGHT)
            return walkTo(field.get(Direction.RIGHT));

        if (move == SingleMove.UP)
            return walkTo(field.get(Direction.UP));

        throw new UnsupportedOperationException("SingleMove is not registered.");
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
        if (newField != null) {
            field = newField;
            return true;
        }
        return false;
    }
}
