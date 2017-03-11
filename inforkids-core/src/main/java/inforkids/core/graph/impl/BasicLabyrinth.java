package inforkids.core.graph.impl;

import inforkids.core.graph.Direction;
import inforkids.core.graph.Field;
import inforkids.core.graph.Labyrinth;

import java.util.Iterator;

/**
 * TODO
 *
 * @author Dominic Parga Cacheiro
 */
public class BasicLabyrinth implements Labyrinth {

    private final Field[] fields;
    private final int rows;
    private final int columns;


    public BasicLabyrinth(int rows, int columns) {

        fields = new Field[rows * columns];
        this.rows = rows;
        this.columns = columns;

        fillEmpy();
        linkAllFields();
    }

    public BasicLabyrinth(int rows, int columns, String fieldStr) {

        this.rows = rows;
        this.columns = columns;
        fields = new Field[rows * columns];

        fill(fieldStr);
        linkAllFields();
    }


    /*
    |=============|
    | (i) Matrix2 |
    |=============|
    */
    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public Field get(int row, int column) {
        if (isInputCorrect(row, column))
            return fields[row * columns + column];
        else
            return null;
    }

    @Override
    public void set(int row, int column, Field field) {
        fields[row * columns + column] = field;
    }

    @Override
    public Iterator<Field> iterator() {
        return new Iterator<Field>() {

            int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < fields.length;
            }

            @Override
            public Field next() {
                return fields[idx++];
            }
        };
    }

    /*
    |=======|
    | utils |
    |=======|
    */
    private boolean isInputCorrect(int row, int column) {

        if (row < 0)
            return false;

        if (column < 0)
            return false;

        if (row >= rows)
            return false;

        if (column >= columns)
            return false;

        return true;
    }

    private void fillEmpy() {

        /* fill borders with walls */
        for (int row = 0; row < rows; row++) {
            set(row, 0, new BasicField(Field.Type.WALL));
            set(row, columns - 1, new BasicField(Field.Type.WALL));
        }

        /* fill borders with walls */
        for (int column = 0; column < columns; column++) {
            set(0, column, new BasicField(Field.Type.WALL));
            set(rows - 1, column, new BasicField(Field.Type.WALL));
        }


        /* fill inner fields with grounds */
        for (int row = 1; row < rows - 1; row++)
            for (int column = 1; column < columns - 1; column++)
                set(row, column, new BasicField(Field.Type.GROUND));
    }

    private void fill(String fieldStr) {

        fieldStr = fieldStr.trim();

        int idx = 0;
        for (char c : fieldStr.toCharArray()) {

            if (c == Field.GROUND_SYMBOL || c == Field.GOAL_SYMBOL) {

                fields[idx++] = new BasicField(Field.Type.GROUND, c == Field.GOAL_SYMBOL);

            } else if (c == Field.WALL_SYMBOL) {

                fields[idx++] = new BasicField(Field.Type.WALL);

            }
        }
    }

    /**
     * Sets all neighbour relations of ground-fields for every neighbour not being a wall.
     */
    private void linkAllFields() {
        for (int row = 0; row < rows; row ++) {
            for (int column = 0; column < columns; column++) {

                Field field = get(row, column);
                if (field.getType() != Field.Type.WALL) {

                    Field current;

                    /* left */
                    current = get(row, column - 1);
                    if (current != null)
                        if (current.getType() != Field.Type.WALL)
                            field.set(Direction.LEFT, current);

                    /* bottom */
                    current = get(row + 1, column);
                    if (current != null)
                        if (current.getType() != Field.Type.WALL)
                            field.set(Direction.DOWN, current);

                    /* right */
                    current = get(row, column + 1);
                    if (current != null)
                        if (current.getType() != Field.Type.WALL)
                            field.set(Direction.RIGHT, current);

                    /* top */
                    current = get(row - 1, column);
                    if (current != null)
                        if (current.getType() != Field.Type.WALL)
                            field.set(Direction.UP, current);
                }
            }
        }
    }
}
