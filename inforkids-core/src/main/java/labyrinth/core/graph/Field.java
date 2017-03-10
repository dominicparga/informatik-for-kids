package labyrinth.core.graph;

import java.util.ArrayList;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Field {

    static boolean isType(Field field, Type type) {
        if (field == null)
            return false;
        return field.getType() == type;
    }

    Type getType();

    boolean isGoalField();

    void setGoalField(boolean isGoalField);

    ArrayList<Field> getNeighbours();

    /**
     * @param direction
     * @return Neighbour in the specified direction
     */
    Field get(Direction direction);

    void set(Direction direction, Field field);


    enum Type { WALL, GROUND }
    char WALL_SYMBOL = 'W';
    char GROUND_SYMBOL = 'G';
    char GOAL_SYMBOL = '*';
}
