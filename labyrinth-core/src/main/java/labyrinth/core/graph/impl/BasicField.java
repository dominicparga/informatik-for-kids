package labyrinth.core.graph.impl;

import labyrinth.core.graph.Direction;
import labyrinth.core.graph.Field;

import java.util.ArrayList;

/**
 * @author Dominic Parga Cacheiro
 */
public class BasicField implements Field {

    public final Type type;
    private boolean isGoalField;
    private Field left, bottom, right, top;

    public BasicField(Type type) {
        this(type, false);
    }

    public BasicField(Type type, boolean isGoalField) {
        this.type = type;
        this.isGoalField = isGoalField;
    }


    /*
    |===========|
    | (i) Field |
    |===========|
    */
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean isGoalField() {
        return isGoalField;
    }

    @Override
    public void setGoalField(boolean isGoalField) {
        this.isGoalField = isGoalField;
    }

    @Override
    public ArrayList<Field> getNeighbours() {
        ArrayList<Field> neighbours = new ArrayList<>(4);

        if (left != null)
            neighbours.add(left);

        if (bottom != null)
            neighbours.add(bottom);

        if (right != null)
            neighbours.add(right);

        if (top != null)
            neighbours.add(top);

        return neighbours;
    }

    @Override
    public Field get(Direction direction) {

        switch (direction) {
            case LEFT:
                return left;
            case DOWN:
                return bottom;
            case RIGHT:
                return right;
            case UP:
                return top;
            default:
                throw new UnsupportedOperationException("This direction is not supported.");
        }
    }

    @Override
    public void set(Direction direction, Field field) {
        switch (direction) {
            case LEFT:
                left = field;
                break;
            case DOWN:
                bottom = field;
                break;
            case RIGHT:
                right = field;
                break;
            case UP:
                top = field;
                break;
        }
    }
}
