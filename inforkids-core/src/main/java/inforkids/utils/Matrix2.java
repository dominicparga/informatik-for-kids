package inforkids.utils;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Matrix2<E> extends Iterable<E> {

    int getRowCount();

    int getColumnCount();

    E get(int row, int column);

    void set(int row, int column, E e);
}
