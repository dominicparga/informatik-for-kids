package inforkids.core.move;

/**
 * @author Dominic Parga Cacheiro
 */
public interface MultiMove extends Move, Iterable<SingleMove>{

    void add(SingleMove move);

    void add(MultiMove move);
}
