package inforkids.core.move;

/**
 * @author Dominic Parga Cacheiro
 */
public interface SingleMove extends Move {

    SingleMove LEFT = new SingleMove() {};
    SingleMove DOWN = new SingleMove() {};
    SingleMove RIGHT = new SingleMove() {};
    SingleMove UP = new SingleMove() {};
}
