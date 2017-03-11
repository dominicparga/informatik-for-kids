package inforkids.utils;

/**
 * @author Dominic Parga Cacheiro
 */
public interface Matrix2<E> extends Iterable<E> {

    int getRows();

    int getColumns();

    E get(int row, int column);

    void set(int row, int column, E e);
}
