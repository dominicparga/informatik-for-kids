package inforkids.core.graph.impl;

import inforkids.core.graph.Direction;
import inforkids.core.graph.Field;
import inforkids.core.graph.Labyrinth;
import inforkids.core.player.BasicPlayer;
import inforkids.core.player.Player;

import java.util.HashMap;
import java.util.Iterator;

/**
 * TODO
 *
 * @author Dominic Parga Cacheiro
 */
public class BasicLabyrinth implements Labyrinth {

    private final HashMap<Integer, HashMap<Integer, Field>> fields;
    private Field startField;
    private final Player player;
    private int rows;
    private int columns;


    public BasicLabyrinth(int rows, int columns) {

        player = new BasicPlayer();
        fields = new HashMap<>(rows);

        StringBuilder builder = new StringBuilder();
        /* fill middle rows with one wall, grounds and one wall */
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                boolean isWall = false;

                /* first row */
                isWall = row == 0
                        /* last row */
                        || row == rows - 1
                        /* first column */
                        || column == 0
                        /* last column */
                        || column == columns - 1;

                if (isWall)
                    builder.append(Field.WALL_SYMBOL);
                else {
                    if (row == 1 && column == 1)
                        builder.append(Field.GOAL_SYMBOL);
                    else if (row == rows - 2 && column == columns - 2)
                        builder.append(Field.START_SYMBOL);
                    else
                        builder.append(Field.GROUND_SYMBOL);
                }
            }
            builder.append(Field.ROW_SEPARATOR);
        }

        fill(builder.toString());
        linkAllFields();
    }

    /**
     *
     * @param fieldStr format: "rows,columns,field" (other chars, e.g. spaces, are ignored)
     */
    public BasicLabyrinth(String fieldStr) {

        player = new BasicPlayer();
        fields = new HashMap<>();

        fill(fieldStr);
        linkAllFields();
    }


    /*
    |===============|
    | (i) Labyrinth |
    |===============|
    */
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Field getStartField() {
        return startField;
    }

    /*
        |=============|
        | (i) Matrix2 |
        |=============|
        */
    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    @Override
    public Field get(int row, int column) {
        if (isInputCorrect(row, column))
            return getRow(row).get(column);
        else
            return null;
    }

    @Override
    public void set(int row, int column, Field field) {
        if (isInputCorrect(row, column))
            getRow(row).put(column, field);
        else
            throw new IllegalArgumentException();
    }

    /**
     * @return An iterator over all fields; empty fields are returned as {@code null} object.
     */
    @Override
    public Iterator<Field> iterator() {
        return new FieldIterator(this);
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

    private HashMap<Integer, Field> getRow(int row) {

        HashMap<Integer, Field> rowFields = fields.get(row);

        if (rowFields == null) {
            rowFields = new HashMap<>(columns);
            fields.put(row, rowFields);
        }

        return rowFields;
    }

    private void fill(String fieldStr) {

        fieldStr = fieldStr.trim();
        fieldStr = fieldStr.toLowerCase();

        String[] lines = fieldStr.split("" + Field.ROW_SEPARATOR);

        int row = 0;
        for (String line : lines) {

            boolean rowHasField = false;

            int column = 0;
            for (char c : line.toCharArray()) {

                if (c == Field.GROUND_SYMBOL || c == Field.START_SYMBOL || c == Field.GOAL_SYMBOL) {
                    Field field = new BasicField(Field.Type.GROUND, c == Field.GOAL_SYMBOL);
                    getRow(row).put(column++, field);

                    if (c == Field.START_SYMBOL) {
                        startField = field;
                        player.setField(startField);
                    }

                    rowHasField = true;
                } else if (c == Field.WALL_SYMBOL) {
                    Field field = new BasicField(Field.Type.WALL);
                    getRow(row).put(column++, field);
                    rowHasField = true;
                } else if (c == Field.EMPTY_SYMBOL) {
                    column++;
                    rowHasField = true;
                }
            }

            columns = Math.max(columns, column);
            if (rowHasField)
                row++;
        }

        rows = row;
    }

    /**
     * Sets all neighbour relations of ground-fields for every neighbour not being a wall.
     */
    private void linkAllFields() {
        for (int row = 0; row < rows; row ++) {
            for (int column = 0; column < columns; column++) {

                Field field = get(row, column);
                if (field == null)
                    continue;

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


    private class FieldIterator implements Iterator<Field> {

        private int row;
        private int column;
        private Labyrinth labyrinth;

        public FieldIterator(Labyrinth labyrinth) {
            row = 0;
            column = 0;
            this.labyrinth = labyrinth;
        }

        @Override
        public boolean hasNext() {
            return row < rows && column < columns;
        }

        @Override
        public Field next() {

            Field field = labyrinth.get(row, column++);

            if (column == labyrinth.getColumnCount()) {
                row++;
                column = 0;
            }

            return field;
        }

    }
}
