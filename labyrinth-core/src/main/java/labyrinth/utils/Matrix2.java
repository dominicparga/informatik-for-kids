package labyrinth.utils;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Matrix2<E> {

    E get(int row, int column);

    void set(int row, int column, E e);

    int getRowDimension();

    int getColumnDimension();
}
