package inforkids.core.move;

/**
 * @author Dominic Parga Cacheiro
 */
public interface SingleMove extends Move {

    SingleMove LEFT = new SingleMove() {
        @Override
        public String toString() {
            return "웃←";
        }
    };
    SingleMove DOWN = new SingleMove() {
        @Override
        public String toString() {
            return "웃↓";
        }
    };
    SingleMove RIGHT = new SingleMove() {
        @Override
        public String toString() {
            return "웃→";
        }
    };
    SingleMove UP = new SingleMove() {
        @Override
        public String toString() {
            return "웃↑";
        }
    };
}
