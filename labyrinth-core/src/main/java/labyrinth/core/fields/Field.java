package labyrinth.core.fields;

/**
 * @author Dominic Parga Cacheiro
 */
public abstract class Field {

    private final char symbol;

    public Field(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "" + symbol;
    }
}
