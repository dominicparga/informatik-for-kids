package labyrinth.core;

import labyrinth.core.fields.Field;
import labyrinth.core.fields.Ground;
import labyrinth.core.fields.Wall;
import labyrinth.utils.Matrix2;

/**
 * TODO
 *
 * @author Dominic Parga Cacheiro
 */
public class Labyrinth implements Matrix2<Field> {

    private final Field[][] fields;
    private final int rowDim;
    private final int columnDim;


    public Labyrinth(int rowDim, int columnDim) {

        this.rowDim = rowDim;
        this.columnDim = columnDim;
        fields = new Field[rowDim + 2][columnDim + 2];

        /* fill borders with walls */
        for (int row = 0; row < rowDim + 2; row++) {
            // fill border columns with walls
            fields[row][0] = new Wall();
            fields[row][getColumnDimension() + 1] = new Wall();
        }

        for (int column = 0; column < columnDim + 2; column++) {
            // fill border rows with walls
            fields[0][column] = new Wall();
            fields[getRowDimension() + 1][column] = new Wall();
        }


        /* fill inner fields with grounds */
        for (int row = 0; row < rowDim; row++)
            for (int column = 0; column < columnDim; column++)
                set(row, column, new Ground());
    }


    @Override
    public String toString() {
        return super.toString();
    }

    /*
        |=============|
        | (i) Matrix2 |
        |=============|
        */
    @Override
    public Field get(int row, int column) {
        if (row < 0)
            throw new IndexOutOfBoundsException("Row has to be >= 0; actual = " + row);
        if (column < 0)
            throw new IndexOutOfBoundsException("Column has to be >= 0; actual = " + column);
        if (row >= getRowDimension())
            throw new IndexOutOfBoundsException("Row has to be smaller than labyrinth size (" + getRowDimension() +
                    "); actual = " +
                    row);
        if (column >= getColumnDimension())
            throw new IndexOutOfBoundsException("Column has to be smaller than labyrinth size (" + getColumnDimension()
                    + "); actual = " + column);

        return fields[row + 1][column + 1];
    }

    @Override
    public void set(int row, int column, Field field) {
        fields[row + 1][column + 1] = field;
    }

    @Override
    public int getRowDimension() {
        return rowDim;
    }

    @Override
    public int getColumnDimension() {
        return columnDim;
    }
}
